package ar.edu.utn.frc.backend.rutas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.rutas.model.EstadoTramo;
import ar.edu.utn.frc.backend.rutas.repository.EstadoTramoRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EstadoDataLoader implements CommandLineRunner {

    private final EstadoTramoRepository estadoTramoRepository;

    @Override
    public void run(String... args) {
        crearSiNoExiste("ESTIMADO", "El tramo no tiene camión asignado.");
        crearSiNoExiste("ASIGNADO", "El tramo tiene camión asignado y está listo para ser iniciado.");
        crearSiNoExiste("INICIADO", "El tramo ha iniciado.");
        crearSiNoExiste("FINALIZADO", "El tramo ha finalizado.");
    }

    private void crearSiNoExiste(String codigo, String descripcion) {
        estadoTramoRepository.findByCodigo(codigo)
                .orElseGet(() -> estadoTramoRepository.save(EstadoTramo.builder()
                        .codigo(codigo)
                        .descripcion(descripcion)
                        .build()));
    }
}
