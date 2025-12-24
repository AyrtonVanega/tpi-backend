package ar.edu.utn.frc.backend.solicitudes.service.interfaces;

import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;

public interface IHistorialEstadoContenedorService {

    void registarCambioEstado(Contenedor contenedor, String codigoEstadoNuevo);
}
