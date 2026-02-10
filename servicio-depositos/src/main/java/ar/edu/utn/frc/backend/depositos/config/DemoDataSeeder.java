package ar.edu.utn.frc.backend.depositos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.depositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.repository.DepositoRepository;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IDepositoService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DemoDataSeeder implements CommandLineRunner {

    private final DepositoRepository depositoRepository;
    private final IDepositoService depositoService;

    @Override
    public void run(String... args) {
        // Verifica si ya hay datos para no duplicar
        if (depositoRepository.count() == 0) {
            crearDepositoDemo("1130 Tte. Ibañez", -32.40222, -63.25081, "Villa María", 1500);
            crearDepositoDemo("200 Av. Godoy Cruz", -33.15292, -64.36049, "Río Cuarto", 2000);
            crearDepositoDemo("1300 Brett", -33.76618, -61.96444, "Venado Tuerto", 1230);
            crearDepositoDemo("155 Tacuarí", -33.31680, -66.34633, "San Luis", 2100);
        }
    }

    private void crearDepositoDemo(String direccion, double latitud, double longitud, String nombreCiudad,
            double costoEstadiaDiaria) {
        DepositoRequestDto dto = new DepositoRequestDto(direccion, latitud, longitud, nombreCiudad, costoEstadiaDiaria);
        depositoService.crear(dto);
    }
}
