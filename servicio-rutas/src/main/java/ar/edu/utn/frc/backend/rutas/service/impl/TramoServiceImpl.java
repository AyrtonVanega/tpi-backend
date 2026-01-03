package ar.edu.utn.frc.backend.rutas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.client.dto.DepositoDto;
import ar.edu.utn.frc.backend.rutas.client.dto.OsrmLegDto;
import ar.edu.utn.frc.backend.rutas.client.dto.OsrmRouteDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoTentativoDto;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITramoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TramoServiceImpl implements ITramoService {

    @Override
    public List<TramoTentativoDto> calcularTramosTentativos(OsrmRouteDto route, Long idOrigen, Long idDestino,
            List<DepositoDto> depositos) {

        List<TramoTentativoDto> tramos = new ArrayList<>();
        List<OsrmLegDto> legs = route.getLegs();
        int totalLegs = legs.size();

        for (int i = 0; i < totalLegs; i++) {
            OsrmLegDto leg = legs.get(i);
            TramoTentativoDto tramo = new TramoTentativoDto();

            // Setea Orden y Distancia
            tramo.setOrden(i + 1);
            tramo.setDistancia(leg.getDistance() / 1000.0);

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
            // tramo.setCostoEstimado();

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
}
