package ar.edu.utn.frc.backend.rutas.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.client.dto.CamionDto;
import ar.edu.utn.frc.backend.rutas.client.dto.DepositoDto;
import ar.edu.utn.frc.backend.rutas.client.dto.OsrmLegDto;
import ar.edu.utn.frc.backend.rutas.client.dto.OsrmRouteDto;
import ar.edu.utn.frc.backend.rutas.dto.CostoTramoResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.DetalleCostoTramoDto;
import ar.edu.utn.frc.backend.rutas.dto.PatchTramoDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoTentativoDto;
import ar.edu.utn.frc.backend.rutas.exception.BusinessException;
import ar.edu.utn.frc.backend.rutas.exception.ResourceNotFoundException;
import ar.edu.utn.frc.backend.rutas.mapper.TramoMapper;
import ar.edu.utn.frc.backend.rutas.model.DetalleCostoTramo;
import ar.edu.utn.frc.backend.rutas.model.EstadoTramo;
import ar.edu.utn.frc.backend.rutas.model.Ruta;
import ar.edu.utn.frc.backend.rutas.model.Tramo;
import ar.edu.utn.frc.backend.rutas.model.TramoId;
import ar.edu.utn.frc.backend.rutas.model.Tramo.TipoTramo;
import ar.edu.utn.frc.backend.rutas.repository.TramoRepository;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IDetalleCostoTramoService;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IEstadoTramoService;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITramoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TramoServiceImpl implements ITramoService {

    private final IDetalleCostoTramoService detalleTramoService;
    private final IEstadoTramoService estadoTramoService;
    private final TramoRepository tramoRepository;
    private final TramoMapper tramoMapper;

    @Override
    public List<TramoTentativoDto> calcularTramosTentativos(OsrmRouteDto route, Long idOrigen, Long idDestino,
            List<DepositoDto> depositos, double costoKmBase, double consumoCombustibleAprox,
            double valorLitroCombustible) {

        List<TramoTentativoDto> tramos = new ArrayList<>();
        List<OsrmLegDto> legs = route.getLegs();
        int totalLegs = legs.size();
        double distancia = 0.0;

        for (int i = 0; i < totalLegs; i++) {
            OsrmLegDto leg = legs.get(i);
            TramoTentativoDto tramo = new TramoTentativoDto();

            // Setea Orden y Distancia
            tramo.setOrden(i + 1);
            distancia = leg.getDistance() / 1000.0;
            tramo.setDistancia(distancia);

            // Setea las Ubicaciones de Origen y Destino
            // Ubicacion de Origen
            if (i == 0) {
                tramo.setIdUbicacionOrigen(idOrigen);
            } else {
                tramo.setIdUbicacionOrigen(depositos.get(i - 1).getIdDeposito());
            }

            // Ubicacion de Destino
            if (i == totalLegs - 1) {
                tramo.setIdUbicacionDestino(idDestino);
            } else {
                tramo.setIdUbicacionDestino(depositos.get(i).getIdDeposito());
            }

            // Determina el TipoTramo
            tramo.setTipo(determinarTipoTramo(i, totalLegs));

            // Calcula el Costo Estimado
            tramo.setCostoEstimado(
                    calcularCostoEstimado(distancia, costoKmBase, consumoCombustibleAprox, valorLitroCombustible));

            // Agrega el Tramo a la lista
            tramos.add(tramo);
        }

        return tramos;
    }

    private String determinarTipoTramo(int index, int totalLegs) {
        if (totalLegs == 1) {
            return "ORIGEN_DESTINO";
        }
        if (index == 0) {
            return "ORIGEN_DEPOSITO";
        }
        if (index == totalLegs - 1) {
            return "DEPOSITO_DESTINO";
        }
        return "DEPOSITO_DEPOSITO";
    }

    @Override
    public double calcularCostoEstimado(double distancia, double costoKmBase, double consumoCombustibleAprox,
            double valorLitroCombustible) {

        return distancia * (costoKmBase + consumoCombustibleAprox * valorLitroCombustible);
    }

    @Override
    public void crearTramos(Ruta ruta, List<TramoTentativoDto> tramosDtos) {

        // Busca el estado Inicial
        EstadoTramo estado = estadoTramoService.buscarPorCodigo("ESTIMADO");

        for (TramoTentativoDto dto : tramosDtos) {
            // Mapea datos simples DTO -> Entity
            Tramo tramo = tramoMapper.toEntity(dto);

            // Arma y setea el id
            TramoId idTramo = new TramoId(ruta.getIdRuta(), dto.getOrden());
            tramo.setIdTramo(idTramo);
            tramo.setRuta(ruta);

            // Setea el estado inicial
            tramo.setEstado(estado);

            // Setea el tipo de tramo
            tramo.setTipoTramo(TipoTramo.valueOf(dto.getTipo()));

            // Guarda en la BD
            tramoRepository.save(tramo);
        }
    }

    @Override
    public void asignarCamion(Long idRuta, int orden, PatchTramoDto dto) {
        TramoId idTramo = new TramoId(idRuta, orden);

        Tramo tramo = tramoRepository.findById(idTramo)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException(
                            "Tramo no encontrado - idRuta: " + idTramo.getIdRuta() + ", orden: " + idTramo.getOrden());
                });

        String estadoTramo = tramo.getEstado().getCodigo();
        if (!estadoTramo.equals("ESTIMADO") && !estadoTramo.equals("ASIGNADO")) {
            log.warn("Intento de asignar camion a Tramo en estado {} - idRuta: {}, orden: {}", estadoTramo, idRuta,
                    orden);
            throw new BusinessException(
                    "No se le puede asignar camion a este Tramo - idRuta:" + idRuta + ", orden: " + orden);
        }

        if (!dto.isDisponibilidad()) {
            log.warn("Intento de asignar camion no disponible al Tramo - idRuta: {}, orden: {}, patenteCamion: {}",
                    idRuta, orden, dto.getPatenteCamion());
            throw new BusinessException("El Camion " + dto.getPatenteCamion() + " no esta disponible");
        }

        if (dto.getVolumenCamion() < dto.getVolumenContenedor() || dto.getPesoCamion() < dto.getPesoContenedor()) {
            log.warn(
                    "No se pudo asignar el camion {} al Tramo - idRuta: {}, orden: {}: capacidad insuficiente",
                    dto.getPatenteCamion(), idRuta, orden);
            throw new BusinessException("El Camion " + dto.getPatenteCamion() + " no puede transportar el contenedor");
        }

        tramo.setPatenteCamion(dto.getPatenteCamion());

        EstadoTramo estado = estadoTramoService.buscarPorCodigo("ASIGNADO");
        tramo.setEstado(estado);

        log.info("Camion {} asignado correctamente al Tramo - idRuta: {}, orden: {}", dto.getPatenteCamion(), idRuta,
                orden);

        tramoRepository.save(tramo);
    }

    @Override
    public List<TramoResponseDto> obtenerTodos(Ruta ruta) {
        List<Tramo> tramos = ruta.getTramos();

        List<TramoResponseDto> responseList = tramoMapper.toResponseList(tramos);

        for (int i = 0; i < tramos.size(); i++) {
            Tramo tramo = tramos.get(i);
            TramoResponseDto dto = responseList.get(i);

            dto.setOrden(tramo.getIdTramo().getOrden());
            dto.setTipo(tramo.getTipoTramo().toString());
            dto.setCodigoEstado(tramo.getEstado().getCodigo());
            dto.setDescripcionEstado(tramo.getEstado().getDescripcion());
        }

        return responseList;
    }

    @Override
    public Tramo obtenerTramoPorId(Long idRuta, int orden) {
        TramoId idTramo = new TramoId(idRuta, orden);

        return tramoRepository.findById(idTramo)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException(
                            "Tramo no encontrado - idRuta: " + idTramo.getIdRuta() + ", orden: " + idTramo.getOrden());
                });
    }

    @Override
    public void validarInicioTramo(Tramo tramo) {
        boolean anteriorIniciado = tramo.getRuta().getTramos()
                .stream()
                .filter(t -> t.getIdTramo().getOrden() < tramo.getIdTramo().getOrden())
                .anyMatch(t -> t.getFechaHoraFin() == null);

        if (anteriorIniciado) {
            log.warn("Intento de iniciar Tramo sin finalizar el anterior - idRuta: {}, orden: {}",
                    tramo.getIdTramo().getIdRuta(),
                    tramo.getIdTramo().getOrden());
            throw new BusinessException("Todavia hay un Tramo en curso");
        }

        if (!tramo.getEstado().getCodigo().equals("ASIGNADO")) {
            log.warn("Intento de iniciar Tramo en estado {} - idRuta: {}, orden: {}", tramo.getEstado().getCodigo(),
                    tramo.getIdTramo().getIdRuta(), tramo.getIdTramo().getOrden());
            throw new BusinessException(
                    "El Tramo tiene que estar en estado 'ASIGNADO' - idRuta:" + tramo.getIdTramo().getIdRuta()
                            + ", orden: " + tramo.getIdTramo().getOrden());
        }
    }

    @Override
    public void iniciarTramo(Tramo tramo) {
        tramo.setFechaHoraInicio(LocalDateTime.now());

        EstadoTramo estado = estadoTramoService.buscarPorCodigo("INICIADO");
        tramo.setEstado(estado);

        log.info("Tramo iniciado - idRuta: {}, orden: {}", tramo.getIdTramo().getIdRuta(),
                tramo.getIdTramo().getOrden());

        tramoRepository.save(tramo);
    }

    @Override
    public void validarFinalizacionTramo(Tramo tramo) {
        if (!tramo.getEstado().getCodigo().equals("INICIADO")) {
            log.warn("Intento de finalizar Tramo en estado {} - idRuta: {}, orden: {}", tramo.getEstado().getCodigo(),
                    tramo.getIdTramo().getIdRuta(), tramo.getIdTramo().getOrden());
            throw new BusinessException(
                    "El Tramo tiene que estar en estado 'INICIADO' - idRuta:" + tramo.getIdTramo().getIdRuta()
                            + ", orden: " + tramo.getIdTramo().getOrden());
        }
    }

    @Override
    public void finalizarTramo(Tramo tramo, LocalDateTime fechaHora, CamionDto camion, double valorLitroCombustible) {
        tramo.setFechaHoraFin(fechaHora);

        EstadoTramo estado = estadoTramoService.buscarPorCodigo("FINALIZADO");
        tramo.setEstado(estado);

        List<DetalleCostoTramo> detalles = detalleTramoService.crearDetalles(tramo, camion, valorLitroCombustible);
        tramo.setDetallesCostoTramo(detalles);

        tramo.setCostoReal(calcularCostoReal(detalles));

        log.info("Tramo finalizado - idRuta: {}, orden: {}, costo real: {}", tramo.getIdTramo().getIdRuta(),
                tramo.getIdTramo().getOrden(), tramo.getCostoReal());

        tramoRepository.save(tramo);
    }

    @Override
    public double calcularCostoReal(List<DetalleCostoTramo> detallesCostoTramo) {
        return detallesCostoTramo.stream()
                .mapToDouble(DetalleCostoTramo::getSubTotal)
                .sum();
    }

    @Override
    public List<TramoResponseDto> obtenerTramosPorPatenteCamionYEstado(String patente, String estado) {
        List<Tramo> tramos = tramoRepository.findByPatenteCamionAndEstado_Codigo(patente, estado);

        List<TramoResponseDto> responseList = tramoMapper.toResponseList(tramos);

        for (int i = 0; i < tramos.size(); i++) {
            Tramo tramo = tramos.get(i);
            TramoResponseDto dto = responseList.get(i);

            dto.setOrden(tramo.getIdTramo().getOrden());
            dto.setTipo(tramo.getTipoTramo().toString());
            dto.setCodigoEstado(tramo.getEstado().getCodigo());
            dto.setDescripcionEstado(tramo.getEstado().getDescripcion());
        }

        return responseList;
    }

    @Override
    public CostoTramoResponseDto mostrarCostos(Tramo tramo) {
        int orden = tramo.getIdTramo().getOrden();
        String patente = tramo.getPatenteCamion();
        List<DetalleCostoTramoDto> detallesCostoTramo = detalleTramoService.obtenerDetalles(tramo);

        return new CostoTramoResponseDto(orden, patente, detallesCostoTramo);
    }
}
