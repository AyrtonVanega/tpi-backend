package ar.edu.utn.frc.backend.solicitudes.infrastructure.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.solicitudes.event.SolicitudCreadaEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SolicitudEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publicarSolicitudCreada(SolicitudCreadaEvent event) {
        kafkaTemplate.send("solicitud-creada", event)
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
