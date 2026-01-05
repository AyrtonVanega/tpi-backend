package ar.edu.utn.frc.backend.rutas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.client.PersonaClient;
import ar.edu.utn.frc.backend.rutas.client.dto.DepositoDto;
import ar.edu.utn.frc.backend.rutas.client.dto.OsrmLegDto;
import ar.edu.utn.frc.backend.rutas.client.dto.OsrmRouteDto;
import ar.edu.utn.frc.backend.rutas.dto.PatchTramoDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoTentativoDto;
import ar.edu.utn.frc.backend.rutas.mapper.TramoMapper;
import ar.edu.utn.frc.backend.rutas.model.EstadoTramo;
import ar.edu.utn.frc.backend.rutas.model.Ruta;
import ar.edu.utn.frc.backend.rutas.model.Tramo;
import ar.edu.utn.frc.backend.rutas.model.TramoId;
import ar.edu.utn.frc.backend.rutas.repository.TramoRepository;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IEstadoTramoService;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITramoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TramoServiceImpl implements ITramoService {

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
            dto.setTipo(null);
            dto.setCodigoEstado(tramo.getEstado().getCodigo());
        }

        return responseList;
    }
}
