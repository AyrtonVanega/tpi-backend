package ar.edu.utn.frc.backend.rutas.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.utn.frc.backend.rutas.client.dto.DepositoDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositoClient {

    private final WebClient depositoWebClient;

    public List<DepositoDto> obtenerDepositosEnBoundingBox(double minLat, double maxLat, double minLon, double maxLon) {
        return depositoWebClient.get()
                .uri("http://depositos/depositos/internal/{minLat}/{maxLat}/{minLon}/{maxLon}", minLat, maxLat, minLon,
                        maxLon)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DepositoDto>>() {
                })
                .block();
    }
}
