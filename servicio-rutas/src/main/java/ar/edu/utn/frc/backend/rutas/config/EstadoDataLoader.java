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
        crearSiNoExiste("ESTIMADO", "...");
        crearSiNoExiste("ASIGNADO", "...");
        crearSiNoExiste("INICIADO", "...");
        crearSiNoExiste("FINALIZADO", "...");
    }

    private void crearSiNoExiste(String codigo, String descripcion) {
        estadoTramoRepository.findByCodigo(codigo)
                .orElseGet(() -> estadoTramoRepository.save(EstadoTramo.builder()
                        .codigo(codigo)
                        .descripcion(descripcion)
                        .build()));
    }
}
