package ar.edu.utn.frc.backend.personas.infrastructure.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.personas.event.CamionCreadoEvent;
import ar.edu.utn.frc.backend.personas.model.Camion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CamionEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publicarCamionCreado(Camion camion) {
        CamionCreadoEvent event = new CamionCreadoEvent(
                camion.getPatente(),
                camion.getPeso(),
                camion.getVolumen(),
                camion.getConsumoCombustiblePromedio());

        kafkaTemplate.send("camion-creado", event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("ERROR enviando evento", ex);
                    } else {
                        log.info("Evento enviado. Offset={}",
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
