package ar.edu.utn.frc.backend.personas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ServicioPersonasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioPersonasApplication.class, args);
	}

}
