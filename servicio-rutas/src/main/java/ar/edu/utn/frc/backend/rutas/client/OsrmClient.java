package ar.edu.utn.frc.backend.rutas.client;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.utn.frc.backend.rutas.client.dto.OsrmRouteDto;
import ar.edu.utn.frc.backend.rutas.client.dto.OsrmRouteResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OsrmClient {

        private final WebClient osrmWebClient;

        public OsrmRouteDto calcularRutaConOsrm(List<String> coordenadas) {

                // coordenadas debe ser una lista tipo ["lonA,latA", "lonB,latB", "lonC,latC"]
                String coordsString = String.join(";", coordenadas);

                return osrmWebClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/route/v1/driving/" + coordsString)
                                                .queryParam("overview", "false")
                                                .queryParam("steps", "false")
                                                .build())
                                .retrieve()
                                .bodyToMono(OsrmRouteResponseDto.class)
                                .map(resp -> resp.getRoutes().get(0))
                                .block();
        }
}
