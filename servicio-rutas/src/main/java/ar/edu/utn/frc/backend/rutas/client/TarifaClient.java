package ar.edu.utn.frc.backend.rutas.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.utn.frc.backend.rutas.client.dto.ParametroGlobalDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarifaClient {

    private final WebClient tarifaWebClient;

    public ParametroGlobalDto obtenerParametrosGlobales() {
        return tarifaWebClient.get()
                .uri("http://tarifas/parametro-global")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ParametroGlobalDto>() {
                })
                .block();
    }
}
