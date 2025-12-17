package ar.edu.utn.frc.backend.solicitudes.service.interfaces;

import ar.edu.utn.frc.backend.solicitudes.dto.EstadoSolicitudRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.EstadoSolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.service.base.CrudService;

public interface IEstadoSolicitudService extends CrudService<
        EstadoSolicitudResponseDto, EstadoSolicitudRequestDto, Long> {
}
