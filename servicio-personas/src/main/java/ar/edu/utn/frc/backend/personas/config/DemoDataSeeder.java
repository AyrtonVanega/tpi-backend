package ar.edu.utn.frc.backend.personas.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.personas.dto.CreateTransportistaDto;
import ar.edu.utn.frc.backend.personas.repository.TransportistaRepository;
import ar.edu.utn.frc.backend.personas.workflow.RegistrarTransportistaWorkflow;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DemoDataSeeder implements CommandLineRunner {

    private final TransportistaRepository transportistaRepository;
    private final RegistrarTransportistaWorkflow registrarTransportistaWorkflow;

    @Override
    public void run(String... args) {
        // Verifica si ya hay datos para no duplicar
        if (transportistaRepository.count() == 0) {
            crearTransportistaDemo("43647264", "DNI", "José", "Sanchez", "12345678", "jose.sanchez@example.com",
                    LocalDateTime.now().plusYears(1), "AA-000-AA", 25, 6000.0, 1200.0, 0.18);
            crearTransportistaDemo("39876543", "DNI", "Ana", "Martinez", "87654321", "ana.martinez@example.com",
                    LocalDateTime.now().plusYears(1), "BB-111-BB", 45, 30000.0, 2150.0, 0.4);
            crearTransportistaDemo("41234567", "DNI", "Luis", "Gonzalez", "56781234", "luis.gonzalez@example.com",
                    LocalDateTime.now().plusYears(1), "CC-222-CC", 100, 35000.0, 2600.0, 0.45);
        }
    }

    private void crearTransportistaDemo(String doc, String tipoDoc, String nombre, String apellido, String telefono,
            String email, LocalDateTime vencimientoLic, String pantenteCamion, double volCamion, double pesoCamion,
            double costoBaseKmCamion, double consumoCombusProm) {

        CreateTransportistaDto dto = new CreateTransportistaDto(doc, tipoDoc, nombre, apellido, telefono, email,
                vencimientoLic, pantenteCamion, volCamion, pesoCamion, costoBaseKmCamion, consumoCombusProm);

        registrarTransportistaWorkflow.registrarTransportista(dto);
    }
}
