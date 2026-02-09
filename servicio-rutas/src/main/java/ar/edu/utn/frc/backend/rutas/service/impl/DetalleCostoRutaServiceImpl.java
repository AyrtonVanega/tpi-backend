package ar.edu.utn.frc.backend.rutas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.dto.DetalleCostoRutaDto;
import ar.edu.utn.frc.backend.rutas.mapper.DetalleCostoRutaMapper;
import ar.edu.utn.frc.backend.rutas.model.DetalleCostoRuta;
import ar.edu.utn.frc.backend.rutas.model.Ruta;
import ar.edu.utn.frc.backend.rutas.model.Tramo;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IDetalleCostoRutaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetalleCostoRutaServiceImpl implements IDetalleCostoRutaService {

    private final DetalleCostoRutaMapper detalleRutaMapper;

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
                .concepto("Costo De Gestion")
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
                .concepto("Costo Por Tramos")
                .subTotal(costo)
                .ruta(ruta)
                .build();
    }

    @Override
    public DetalleCostoRuta calcularCostoEstadias(Ruta ruta, double costoTotalEstadias) {
        return DetalleCostoRuta.builder()
                .concepto("Costos Por Estadias")
                .subTotal(costoTotalEstadias)
                .ruta(ruta)
                .build();
    }

    @Override
    public List<DetalleCostoRutaDto> obtenerDetalles(Ruta ruta) {
        return detalleRutaMapper.toResponseList(ruta.getDetallesCostoRuta());
    }
}
