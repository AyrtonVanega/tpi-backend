package ar.edu.utn.frc.backend.tarifas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class TarifasApplication {

	public static void main(String[] args) {
		SpringApplication.run(TarifasApplication.class, args);
	}

}
