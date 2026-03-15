package ar.edu.utn.frc.backend.solicitudes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ServicioSolicitudesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioSolicitudesApplication.class, args);
	}

}
