package ar.edu.utn.frc.backend.ServicioSolicitudes.service.interfaces;

import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.SolicitudRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.service.base.CrudService;

public interface ISolicitudService extends CrudService<SolicitudResponseDto, SolicitudRequestDto, Long> {
}
