package ar.edu.utn.frc.backend.solicitudes.service.interfaces;

import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;
import ar.edu.utn.frc.backend.solicitudes.model.EstadoContenedor;

public interface IHistorialEstadoContenedorService {

    void registarCambioEstado(Contenedor contenedor, String codigoEstadoNuevo);

    EstadoContenedor obtenerEstadoActual(Long idContenedor);
}
