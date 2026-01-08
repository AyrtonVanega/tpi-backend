package ar.edu.utn.frc.backend.rutas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.rutas.model.DetalleCostoRuta;
import ar.edu.utn.frc.backend.rutas.model.Ruta;

public interface IDetalleCostoRutaService {
    
    List<DetalleCostoRuta> crearDetalles(Ruta ruta, double costoGestionBase, double costoTotalEstadias);

    DetalleCostoRuta calcularCostoGestion(Ruta ruta, double costoGestionBase);

    DetalleCostoRuta calcularCostoTramos(Ruta ruta);

    DetalleCostoRuta calcularCostoEstadias(Ruta ruta, double costoTotalEstadias);
}
