package ar.edu.utn.frc.backend.depositos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class ServicioDepositosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioDepositosApplication.class, args);
	}

}
