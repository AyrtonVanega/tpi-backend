package ar.edu.utn.frc.backend.personas.service.interfaces;

import ar.edu.utn.frc.backend.personas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.personas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.personas.dto.PatchCamionDto;
import ar.edu.utn.frc.backend.personas.service.base.CrudService;

public interface ICamionService extends CrudService<CamionResponseDto, CamionRequestDto, String> {
    
    void actualizarDisponibilidad(String patente, PatchCamionDto patchCamionDto);
}
