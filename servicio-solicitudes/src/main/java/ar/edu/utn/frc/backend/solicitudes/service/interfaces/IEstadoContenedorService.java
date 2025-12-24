package ar.edu.utn.frc.backend.solicitudes.service.interfaces;

import ar.edu.utn.frc.backend.solicitudes.model.EstadoContenedor;

public interface IEstadoContenedorService {
        
        EstadoContenedor buscarPorCodigo(String codigo);
}
