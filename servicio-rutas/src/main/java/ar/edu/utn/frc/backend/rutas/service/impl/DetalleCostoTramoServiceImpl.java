package ar.edu.utn.frc.backend.rutas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.client.dto.CamionDto;
import ar.edu.utn.frc.backend.rutas.model.DetalleCostoTramo;
import ar.edu.utn.frc.backend.rutas.model.Tramo;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IDetalleCostoTramoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DetalleCostoTramoServiceImpl implements IDetalleCostoTramoService {

    @Override
    public List<DetalleCostoTramo> crearDetalles(Tramo tramo, CamionDto camion, double valorLitroCombustible) {
        List<DetalleCostoTramo> detalles = new ArrayList<>();

        detalles.add(calcularCostoDistancia(tramo, camion));
        detalles.add(calcularCostoConsumoCombustible(tramo, camion, valorLitroCombustible));

        return detalles;
    }

    @Override
    public DetalleCostoTramo calcularCostoDistancia(Tramo tramo, CamionDto camion) {
        double costo = tramo.getDistancia() * camion.getCostoBaseKm();
        return DetalleCostoTramo.builder()
                .subTotal(costo)
                .tramo(tramo)
                .build();
    }

    @Override
    public DetalleCostoTramo calcularCostoConsumoCombustible(Tramo tramo, CamionDto camion, double valorLitroCombustible) {
        double costo = tramo.getDistancia() * camion.getConsumoCombustiblePromedio() * valorLitroCombustible;
        return DetalleCostoTramo.builder()
                .subTotal(costo)
                .tramo(tramo)
                .build();
    }
}
