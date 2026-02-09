package ar.edu.utn.frc.backend.personas.workflow;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.personas.infrastructure.messaging.CamionEventPublisher;
import ar.edu.utn.frc.backend.personas.model.Camion;
import ar.edu.utn.frc.backend.personas.service.interfaces.ICamionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActualizarCamionWorkflow {

    private final ICamionService camionService;
    private final CamionEventPublisher camionEventPublisher;
    
    public void actualizarCamion(String patenteCamion, CamionRequestDto dto) {
        Camion camion = camionService.actualizar(patenteCamion, dto);
        camionEventPublisher.publicarCamionActualizado(camion);
    }
}
