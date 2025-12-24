package ar.edu.utn.frc.backend.solicitudes.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.solicitudes.model.EstadoSolicitud;
import ar.edu.utn.frc.backend.solicitudes.repository.EstadoSolicitudRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EstadoSolicitudDataLoader implements CommandLineRunner {
    
    private final EstadoSolicitudRepository estadoSolicitudRepository;

    @Override
    public void run(String... args) {
        crearSiNoExiste("BORRADOR", "La solicitud no tiene ruta asignada.");
        crearSiNoExiste("PROGRAMADA", "Las dimensiones del contenedor han sido confirmadas y se le ha asignado ruta a la solicitud.");
        crearSiNoExiste("EN_TRANSITO", "La solicitud ha iniciado la ruta.");
        crearSiNoExiste("ENTREGADA", "La solicitud fue entregada exitosamente en el destino.");
        crearSiNoExiste("CANCELADA", "La solicitud fue cancelada antes de asignarse una ruta.");
    }

    private void crearSiNoExiste(String codigo, String descripcion) {
        estadoSolicitudRepository.findByCodigo(codigo)
                .orElseGet(() -> estadoSolicitudRepository.save(EstadoSolicitud.builder()
                        .codigo(codigo)
                        .descripcion(descripcion)
                        .build()));
    }

}
