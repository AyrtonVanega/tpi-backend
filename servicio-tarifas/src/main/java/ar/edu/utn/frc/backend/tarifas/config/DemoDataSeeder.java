package ar.edu.utn.frc.backend.tarifas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.tarifas.dto.CreateTarifaDto;
import ar.edu.utn.frc.backend.tarifas.repository.TarifaRepository;
import ar.edu.utn.frc.backend.tarifas.workflow.CrearTarifaWorkflow;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Order(2)
public class DemoDataSeeder implements CommandLineRunner {

    private final CrearTarifaWorkflow crearTarifaWorkflow;
    private final TarifaRepository tarifaRepository;

    @Override
    public void run(String... args) {
        // Verifica si ya hay datos para no duplicar
        if (tarifaRepository.count() == 0) {
            crearTarifaDemo(0, 5000, 0, 20, 1100);
            crearTarifaDemo(5000, 25000, 20, 40, 2000);
            crearTarifaDemo(25000, 30000, 40, 90, 2500);
        }
    }

    private void crearTarifaDemo(double pMin, double pMax, double vMin, double vMax, double costo) {
        CreateTarifaDto dto = new CreateTarifaDto(pMin, pMax, vMin, vMax, costo);
        crearTarifaWorkflow.crearTarifa(dto);
    }
}
