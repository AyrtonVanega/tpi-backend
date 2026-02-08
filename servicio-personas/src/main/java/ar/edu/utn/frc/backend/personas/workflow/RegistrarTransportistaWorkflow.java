package ar.edu.utn.frc.backend.personas.workflow;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.dto.CreateTransportistaDto;
import ar.edu.utn.frc.backend.personas.infrastructure.messaging.CamionEventPublisher;
import ar.edu.utn.frc.backend.personas.model.Camion;
import ar.edu.utn.frc.backend.personas.model.Transportista;
import ar.edu.utn.frc.backend.personas.service.interfaces.ICamionService;
import ar.edu.utn.frc.backend.personas.service.interfaces.ITransportistaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrarTransportistaWorkflow {

    private final ITransportistaService transportistaService;
    private final ICamionService camionService;
    private final CamionEventPublisher camionEventPublisher;

    public void registrarTransportista(CreateTransportistaDto dto) {

        Transportista transportista = transportistaService.crear(dto);

        Camion camion = camionService.crear(
                dto.getPatenteCamion(),
                dto.getVolumenCamion(),
                dto.getPesoCamion(),
                dto.getCostoBaseKmCamion(),
                dto.getConsumoCombustiblePromedioCamion(),
                transportista);

        camionEventPublisher.publicarCamionCreado(camion);
    }
}
