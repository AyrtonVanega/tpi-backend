package ar.edu.utn.frc.backend.ServicioSolicitudes.service.interfaces;

import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.EstadoSolicitudRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.EstadoSolicitudResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.service.base.CrudService;

public interface IEstadoSolicitudService extends CrudService<
        EstadoSolicitudResponseDto, EstadoSolicitudRequestDto, Long> {
}
