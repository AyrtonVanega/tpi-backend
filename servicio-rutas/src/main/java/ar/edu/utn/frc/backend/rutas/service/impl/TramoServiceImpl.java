package ar.edu.utn.frc.backend.rutas.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.client.DepositoClient;
import ar.edu.utn.frc.backend.rutas.client.PersonaClient;
import ar.edu.utn.frc.backend.rutas.client.SolicitudClient;
import ar.edu.utn.frc.backend.rutas.client.dto.DepositoDto;
import ar.edu.utn.frc.backend.rutas.client.dto.OsrmLegDto;
import ar.edu.utn.frc.backend.rutas.client.dto.OsrmRouteDto;
import ar.edu.utn.frc.backend.rutas.dto.FinalizarTramoDto;
import ar.edu.utn.frc.backend.rutas.dto.PatchTramoDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoTentativoDto;
import ar.edu.utn.frc.backend.rutas.mapper.TramoMapper;
import ar.edu.utn.frc.backend.rutas.model.EstadoTramo;
import ar.edu.utn.frc.backend.rutas.model.Ruta;
import ar.edu.utn.frc.backend.rutas.model.Tramo;
import ar.edu.utn.frc.backend.rutas.model.TramoId;
import ar.edu.utn.frc.backend.rutas.model.Tramo.TipoTramo;
import ar.edu.utn.frc.backend.rutas.repository.TramoRepository;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IEstadoTramoService;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITramoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TramoServiceImpl implements ITramoService {

    private final SolicitudClient solicitudClient;
    private final DepositoClient depositoClient;
    private final PersonaClient personaClient;
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

        if (!dto.isDisponibilidad()) {
            log.error("El Camion {} no esta disponible", dto.getPatenteCamion());
            throw new RuntimeException();
        }

        if (dto.getVolumenCamion() < dto.getVolumenContenedor() || dto.getPesoCamion() < dto.getPesoContenedor()) {
            log.error("El Camion {} no puede transportar el contenedor", dto.getPatenteCamion());
            throw new RuntimeException();
        }

        TramoId idTramo = new TramoId(idRuta, orden);

        Tramo tramo = tramoRepository.findById(idTramo)
                .orElseThrow(() -> {
                    log.error("Tramo no encontrado - idRuta:{}, orden:{}",
                            idTramo.getIdRuta(),
                            idTramo.getOrden());
                    return new RuntimeException();
                });

        tramo.setPatenteCamion(dto.getPatenteCamion());

        EstadoTramo estado = estadoTramoService.buscarPorCodigo("ASIGNADO");
        tramo.setEstado(estado);

        personaClient.actualizarDisponibilidadCamion(dto.getPatenteCamion(), !dto.isDisponibilidad());

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
        }

        return responseList;
    }

    @Override
    public void iniciarTramo(Long idRuta, int orden) {
        TramoId idTramo = new TramoId(idRuta, orden);

        Tramo tramo = tramoRepository.findById(idTramo)
                .orElseThrow(() -> {
                    log.error("Tramo no encontrado - idRuta:{}, orden:{}",
                            idTramo.getIdRuta(),
                            idTramo.getOrden());
                    return new RuntimeException();
                });

        // Validaciones
        Ruta ruta = tramo.getRuta();
        List<Tramo> tramos = ruta.getTramos();

        boolean anteriorIniciado = tramos.stream()
                .filter(t -> t.getIdTramo().getOrden() < orden)
                .anyMatch(t -> t.getFechaHoraFin() == null);

        if (anteriorIniciado) {
            log.error("Todavia hay un Tramo en curso");
            throw new RuntimeException();
        }

        if (tramo.getFechaHoraInicio() != null) {
            log.error("Este Tramo ya ha iniciado");
            throw new RuntimeException();
        }

        if (tramo.getPatenteCamion() == null) {
            log.error("El Tramo no tiene camion asignado - idRuta:{}, orden:{}",
                    idTramo.getIdRuta(),
                    idTramo.getOrden());
            throw new RuntimeException();
        }

        // Setea la fecha y hora de inicio
        tramo.setFechaHoraInicio(LocalDateTime.now());

        // Cambia el Estado del Tramo
        EstadoTramo estado = estadoTramoService.buscarPorCodigo("INICIADO");
        tramo.setEstado(estado);

        // Finaliza la estadia en caso de que haya estado en un deposito
        Long idSolicitud = ruta.getIdSolicitud();
        if (orden > 1 && orden != tramos.size()) {
            depositoClient.finalizarEstadia(tramo.getIdUbicacionOrigen(), idSolicitud, LocalDateTime.now());
        }

        // En caso de ser el primer Tramo, cambia el estado de la solicitud
        if (orden == 1) {
            solicitudClient.actualizarEstadoSolicitud(idSolicitud, "EN_TRANSITO");
        }

        // Actualiza el estado del contenedor
        // El id de la solicitud es el mismo q el id del contenedor
        solicitudClient.actualizarEstadoContenedor(idSolicitud, "EN_TRANSITO");

        // Guarda los cambios en la BD
        tramoRepository.save(tramo);
    }

    @Override
    public void finalizarTramo(Long idRuta, int orden, FinalizarTramoDto dto) {
        TramoId idTramo = new TramoId(idRuta, orden);

        Tramo tramo = tramoRepository.findById(idTramo)
                .orElseThrow(() -> {
                    log.error("Tramo no encontrado - idRuta:{}, orden:{}",
                            idTramo.getIdRuta(),
                            idTramo.getOrden());
                    return new RuntimeException();
                });

        // Validaciones
        Ruta ruta = tramo.getRuta();

        if (tramo.getFechaHoraInicio() == null) {
            log.error("Este Tramo todavia no ha iniciado");
            throw new RuntimeException();
        }

        if (tramo.getFechaHoraFin() != null) {
            log.error("Este Tramo ya ha finalizado");
            throw new RuntimeException();
        }

        // Setea la fecha y hora de inicio
        LocalDateTime fechaHora = LocalDateTime.now();
        tramo.setFechaHoraFin(fechaHora);

        // Cambia el Estado del Tramo
        EstadoTramo estado = estadoTramoService.buscarPorCodigo("FINALIZADO");
        tramo.setEstado(estado);

        // Calcula y setea el costo real
        double costoReal = calcularCostoReal(
            tramo.getDistancia(),
            dto.getCostoKmBase(),
            dto.getConsumoCombustiblePromedio(),
            dto.getValorLitroCombustible());
        tramo.setCostoReal(costoReal);

        Long idSolicitud = ruta.getIdSolicitud();
        if (orden != ruta.getTramos().size()) {
            // En caso de no ser el ultimo Tramo, crea la estadia y cambia el estado del
            // contenedor
            depositoClient.crearEstadia(tramo.getIdUbicacionDestino(), idSolicitud, fechaHora);
            solicitudClient.actualizarEstadoContenedor(idSolicitud, "EN_DEPOSITO");
        } else {
            // En caso de ser el ultimo Tramo
            // Calcula costo y tiempo real
            double costoRealTotal = 0.0;
            double tiempoReal = 0.0;

            // Cambia el estado del contenedor y finaliza la solicitud
            solicitudClient.actualizarEstadoContenedor(idSolicitud, "ENTREGADO");
            solicitudClient.finalizarSolicitud(idSolicitud, fechaHora, costoRealTotal, tiempoReal);
        }

        // Setea true la disponibilidad del camion
        personaClient.actualizarDisponibilidadCamion(dto.getPatenteCamion(), true);

        // Guarda los cambios en la BD
        tramoRepository.save(tramo);
    }

    @Override
    public double calcularCostoReal(double distancia, double costoKmBase, double consumoCombustiblePromedio,
            double valorLitroCombustible) {

        return distancia * (costoKmBase + consumoCombustiblePromedio * valorLitroCombustible);
    }
}
