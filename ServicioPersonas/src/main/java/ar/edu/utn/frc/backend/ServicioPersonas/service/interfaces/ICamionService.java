package ar.edu.utn.frc.backend.ServicioPersonas.service.interfaces;

import ar.edu.utn.frc.backend.ServicioPersonas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.ServicioPersonas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.ServicioPersonas.service.base.CrudService;

public interface ICamionService extends CrudService<CamionResponseDto, CamionRequestDto, String> {
    
}
