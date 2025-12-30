package ar.edu.utn.frc.backend.tarifas.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.tarifas.model.ParametroGlobal;
import ar.edu.utn.frc.backend.tarifas.repository.ParametroGlobalRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ParametroGlobalDataLoader implements CommandLineRunner {

    private final ParametroGlobalRepository parametroRepository;

    @Override
    public void run(String... args) {
        crearSiNoExiste();
    }

    private void crearSiNoExiste() {
        parametroRepository.findById(1L)
                .orElseGet(() -> parametroRepository.save(ParametroGlobal.builder()
                        .valorLitroCombustible(1500)
                        .costoGestionBase(2000)
                        .ultimaModificacion(LocalDate.now())
                        .build()));
    }
}
