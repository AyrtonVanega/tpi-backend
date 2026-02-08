package ar.edu.utn.frc.backend.personas.workflow;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.infrastructure.messaging.CamionEventPublisher;
import ar.edu.utn.frc.backend.personas.model.Camion;
import ar.edu.utn.frc.backend.personas.service.interfaces.ICamionService;
import ar.edu.utn.frc.backend.personas.service.interfaces.ITransportistaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EliminarTransportistaWorkflow {

    private final ITransportistaService transportistaService;
    private final ICamionService camionService;
    private final CamionEventPublisher camionEventPublisher;

    public void eliminarTransportista(String docTransportista, String tipoDocTransportista) {
        // Obtiene el Camion asociado al Transportista
        Camion camion = camionService.obtenerCamionPorTransportista(docTransportista, tipoDocTransportista);

        // Elimina el Transportista
        transportistaService.eliminar(docTransportista, tipoDocTransportista);

        // Publica el evento
        camionEventPublisher.publicarCamionEliminado(camion);
    }
}
