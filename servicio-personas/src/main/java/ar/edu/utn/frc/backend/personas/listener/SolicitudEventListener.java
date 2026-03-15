package ar.edu.utn.frc.backend.personas.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.personas.event.SolicitudCreadaPersonaEvent;
import ar.edu.utn.frc.backend.personas.service.interfaces.IClienteService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SolicitudEventListener {
    
    private final IClienteService clienteService;

    @KafkaListener(
        topics = "solicitud-creada",
        groupId = "personas-group"
    )
    public void onSolicitudCreada(SolicitudCreadaPersonaEvent event) {
        clienteService.crear(event.getCliente());
    }
}
