package ar.edu.utn.frc.backend.tarifas.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonaClient {

    private final WebClient personaWebClient;

    public Double calcularConsumoPromedio(double pesoMin, double pesoMax, double volMin, double volMax) {
        return personaWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("personas")
                        .path("/camiones/internal/consumo-promedio")
                        .queryParam("pesoMin", pesoMin)
                        .queryParam("pesoMax", pesoMax)
                        .queryParam("volMin", volMin)
                        .queryParam("volMax", volMax)
                        .build())
                .retrieve()
                .bodyToMono(Double.class)
                .block();
    }
}
