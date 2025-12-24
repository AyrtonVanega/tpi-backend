package ar.edu.utn.frc.backend.solicitudes.service.interfaces;

import ar.edu.utn.frc.backend.solicitudes.model.EstadoSolicitud;

public interface IEstadoSolicitudService {

        EstadoSolicitud buscarPorCodigo(String codigo);
}
