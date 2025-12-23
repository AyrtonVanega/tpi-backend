package ar.edu.utn.frc.backend.depositos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.depositos.model.EstadoEstadiaDeposito;
import ar.edu.utn.frc.backend.depositos.repository.EstadoEstadiaDepositoRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EstadoDataLoader implements CommandLineRunner {

    private final EstadoEstadiaDepositoRepository estadoRepository;

    @Override
    public void run(String... args) {
        crearSiNoExiste("ACTIVA", "El contenedor está actualmente alojado en el depósito.");
        crearSiNoExiste("FINALIZADA", "El contenedor fue retirado del depósito");
    }

    private void crearSiNoExiste(String codigo, String descripcion) {
        estadoRepository.findByCodigo(codigo)
                .orElseGet(() -> estadoRepository.save(EstadoEstadiaDeposito.builder()
                        .codigo(codigo)
                        .descripcion(descripcion)
                        .build()));
    }
}