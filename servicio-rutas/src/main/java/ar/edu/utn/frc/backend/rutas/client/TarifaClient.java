package ar.edu.utn.frc.backend.rutas.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.utn.frc.backend.rutas.client.dto.ParametroGlobalDto;

@Service
public class TarifaClient {

    private final WebClient tarifaWebClient;

    public TarifaClient(@Qualifier("internalWebClient") WebClient webClient) {
        this.tarifaWebClient = webClient;
    }

    public ParametroGlobalDto obtenerParametrosGlobales() {
        return tarifaWebClient.get()
                .uri("http://tarifas/parametro-global/internal")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ParametroGlobalDto>() {
                })
                .block();
    }
}
