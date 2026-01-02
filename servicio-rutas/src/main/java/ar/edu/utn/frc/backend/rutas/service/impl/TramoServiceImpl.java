package ar.edu.utn.frc.backend.rutas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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
    public List<TramoTentativoDto> calcularTramosTentativos(OsrmRouteDto route) {
        List<TramoTentativoDto> tramosTentativos = new ArrayList<>();
        int orden = 1;

        for (OsrmLegDto leg : route.getLegs()) {
            TramoTentativoDto tramoTentativo = new TramoTentativoDto();
            tramoTentativo.setOrden(orden++);
            tramoTentativo.setDistancia(leg.getDistance() / 1000.0);

            tramosTentativos.add(tramoTentativo);
        }
        return tramosTentativos;
    }
}
