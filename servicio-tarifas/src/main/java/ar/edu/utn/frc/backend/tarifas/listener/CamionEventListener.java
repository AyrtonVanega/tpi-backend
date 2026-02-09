package ar.edu.utn.frc.backend.tarifas.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.tarifas.event.CamionActualizadoEvent;
import ar.edu.utn.frc.backend.tarifas.event.CamionCreadoEvent;
import ar.edu.utn.frc.backend.tarifas.event.CamionEliminadoEvent;
import ar.edu.utn.frc.backend.tarifas.service.impl.CamionViewService;
import ar.edu.utn.frc.backend.tarifas.service.interfaces.ITarifaService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CamionEventListener {

    private final CamionViewService camionViewService;
    private final ITarifaService tarifaService;

    @KafkaListener(
        topics = "camion-creado",
        groupId = "tarifas-group"
    )
    public void onCamionCreado(CamionCreadoEvent event) {
        camionViewService.crearDesdeEvento(event);
        tarifaService.recalcularConsumoPromedioParaTarifasAfectadas(event);
    }

    @KafkaListener(
        topics = "camion-eliminado",
        groupId = "tarifas-group"
    )
    public void onCamionEliminado(CamionEliminadoEvent event) {
        camionViewService.eliminarDesdeEvento(event);
        tarifaService.recalcularConsumoPromedioParaTarifasAfectadas(event);
    }

    @KafkaListener(
        topics = "camion-actualizado",
        groupId = "tarifas-group"
    )
    public void onCamionActualizado(CamionActualizadoEvent event) {
        camionViewService.actualizarDesdeEvento(event);
        tarifaService.recalcularConsumoPromedioParaTarifasAfectadas(event);
    }
}
