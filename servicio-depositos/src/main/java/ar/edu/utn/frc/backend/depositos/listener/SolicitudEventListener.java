package ar.edu.utn.frc.backend.depositos.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.depositos.event.SolicitudCreadaDepositoEvent;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IUbicacionService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SolicitudEventListener {
    
    private final IUbicacionService ubicacionService;

    @KafkaListener(
        topics = "solicitud-creada",
        groupId = "depositos-group"
    )
    public void onSolicitudCreada(SolicitudCreadaDepositoEvent event) {
        ubicacionService.crearSiNoExiste(event.getOrigen());
        ubicacionService.crearSiNoExiste(event.getDestino());
    }
}
