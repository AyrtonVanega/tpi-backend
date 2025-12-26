package ar.edu.utn.frc.backend.rutas.service.interfaces;

import ar.edu.utn.frc.backend.rutas.model.EstadoTramo;

public interface IEstadoTramoService {
    
    EstadoTramo buscarPorCodigo(String codigo);
}
