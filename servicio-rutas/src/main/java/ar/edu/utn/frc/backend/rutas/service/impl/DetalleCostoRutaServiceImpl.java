package ar.edu.utn.frc.backend.rutas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.model.DetalleCostoRuta;
import ar.edu.utn.frc.backend.rutas.model.Ruta;
import ar.edu.utn.frc.backend.rutas.model.Tramo;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IDetalleCostoRutaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DetalleCostoRutaServiceImpl implements IDetalleCostoRutaService {

    @Override
    public List<DetalleCostoRuta> crearDetalles(Ruta ruta, double costoGestionBase, double costoTotalEstadias) {
        List<DetalleCostoRuta> detalles = new ArrayList<>();

        detalles.add(calcularCostoGestion(ruta, costoGestionBase));
        detalles.add(calcularCostoTramos(ruta));
        detalles.add(calcularCostoEstadias(ruta, costoTotalEstadias));

        return detalles;
    }

    @Override
    public DetalleCostoRuta calcularCostoGestion(Ruta ruta, double costoGestionBase) {
        double costo = costoGestionBase * ruta.getCantidadTramos();
        return DetalleCostoRuta.builder()
                .subTotal(costo)
                .ruta(ruta)
                .build();
    }

    @Override
    public DetalleCostoRuta calcularCostoTramos(Ruta ruta) {
        double costo = ruta.getTramos()
                .stream()
                .mapToDouble(Tramo::getCostoReal)
                .sum();

        return DetalleCostoRuta.builder()
                .subTotal(costo)
                .ruta(ruta)
                .build();
    }

    @Override
    public DetalleCostoRuta calcularCostoEstadias(Ruta ruta, double costoTotalEstadias) {
        return DetalleCostoRuta.builder()
                .subTotal(costoTotalEstadias)
                .ruta(ruta)
                .build();
    }
}
