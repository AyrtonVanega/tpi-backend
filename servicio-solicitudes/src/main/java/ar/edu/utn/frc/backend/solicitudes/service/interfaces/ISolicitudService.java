package ar.edu.utn.frc.backend.solicitudes.service.interfaces;

import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.service.base.CrudService;

public interface ISolicitudService extends CrudService<SolicitudResponseDto, SolicitudRequestDto, Long> {
}
