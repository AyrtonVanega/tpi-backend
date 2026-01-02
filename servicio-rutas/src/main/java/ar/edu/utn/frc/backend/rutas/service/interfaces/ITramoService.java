package ar.edu.utn.frc.backend.rutas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.rutas.client.dto.OsrmRouteDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoTentativoDto;

public interface ITramoService {
    
    List<TramoTentativoDto> calcularTramosTentativos(OsrmRouteDto route);
}
