package ar.edu.utn.frc.backend.solicitudes.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.solicitudes.model.EstadoContenedor;
import ar.edu.utn.frc.backend.solicitudes.repository.EstadoContenedorRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EstadoContenedorDataLoader implements CommandLineRunner {
    
    private final EstadoContenedorRepository estadoContenedorRepository;

    @Override
    public void run(String... args) {
        crearSiNoExiste("EN_ORIGEN", "El contenedor se encuentra en la ubicación de origen y aún no ha sido retirado.");
        crearSiNoExiste("EN_TRANSITO", "El contenedor se encuentra en traslado a través de un tramo.");
        crearSiNoExiste("EN_DEPOSITO", "El contenedor se encuentra almacenado temporalmente en un depósito.");
        crearSiNoExiste("ENTREGADO", "El contenedor se encuentra en la ubicación de destino final.");
    }

    private void crearSiNoExiste(String codigo, String descripcion) {
        estadoContenedorRepository.findByCodigo(codigo)
                .orElseGet(() -> estadoContenedorRepository.save(EstadoContenedor.builder()
                        .codigo(codigo)
                        .descripcion(descripcion)
                        .build()));
    }
}
