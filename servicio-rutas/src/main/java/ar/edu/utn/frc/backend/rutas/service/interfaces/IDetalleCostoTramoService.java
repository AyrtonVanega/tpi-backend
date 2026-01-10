package ar.edu.utn.frc.backend.rutas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.rutas.client.dto.CamionDto;
import ar.edu.utn.frc.backend.rutas.dto.DetalleCostoTramoDto;
import ar.edu.utn.frc.backend.rutas.model.DetalleCostoTramo;
import ar.edu.utn.frc.backend.rutas.model.Tramo;

public interface IDetalleCostoTramoService {

    List<DetalleCostoTramo> crearDetalles(Tramo tramo, CamionDto camion, double valorLitroCombustible);

    DetalleCostoTramo calcularCostoDistancia(Tramo tramo, CamionDto camion);

    DetalleCostoTramo calcularCostoConsumoCombustible(Tramo tramo, CamionDto camion, double valorLitroCombustible);

    List<DetalleCostoTramoDto> obtenerDetalles(Tramo tramo);
}
