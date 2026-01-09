package ar.edu.utn.frc.backend.personas.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.utn.frc.backend.personas.client.dto.TramoResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RutaClient {

    private final WebClient rutaClient;

    public List<TramoResponseDto> buscarTramosPorPatenteYEstado(String patente, String estado) {
        return rutaClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("rutas")
                        .path("/tramos/internal/patente-estado")
                        .queryParam("patenteCamion", patente)
                        .queryParam("estado", estado)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TramoResponseDto>>() {
                })
                .block();
    }
}
