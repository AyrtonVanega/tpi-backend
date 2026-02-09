package ar.edu.utn.frc.backend.tarifas.service.impl;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.tarifas.event.CamionActualizadoEvent;
import ar.edu.utn.frc.backend.tarifas.event.CamionCreadoEvent;
import ar.edu.utn.frc.backend.tarifas.event.CamionEliminadoEvent;
import ar.edu.utn.frc.backend.tarifas.mapper.CamionViewMapper;
import ar.edu.utn.frc.backend.tarifas.model.CamionView;
import ar.edu.utn.frc.backend.tarifas.repository.CamionViewRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CamionViewService {

    private final CamionViewRepository camionViewRepository;
    private final CamionViewMapper camionViewMapper;

    public void crearDesdeEvento(CamionCreadoEvent event) {
        CamionView camionView = camionViewMapper.toEntity(event);
        camionViewRepository.save(camionView);
    }

    public Double calcularConsumoPromedio(double pesoMax, double volMax) {
        Double promedio = camionViewRepository.obtenerPromedioConsumoDeCamionesAptos(pesoMax, volMax);
        return promedio != null ? promedio : 0.0;
    }

    public void eliminarDesdeEvento(CamionEliminadoEvent event) {
        camionViewRepository.deleteById(event.getPatente());
    }

    public void actualizarDesdeEvento(CamionActualizadoEvent event) {
        CamionView camionView = camionViewRepository.findById(event.getPatente())
                .orElseThrow(() -> new RuntimeException("CamionView no encontrado para patente: " + event.getPatente()));

        camionViewMapper.updateFromEvent(event, camionView);
        camionViewRepository.save(camionView);
    }
}
