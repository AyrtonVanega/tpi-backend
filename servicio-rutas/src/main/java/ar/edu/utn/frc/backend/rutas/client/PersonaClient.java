package ar.edu.utn.frc.backend.rutas.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.utn.frc.backend.rutas.client.dto.CamionDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonaClient {

    private final WebClient personaWebClient;

    public CamionDto finalizarRecorridoCamion(String patenteCamion) {
        return personaWebClient.patch()
                .uri("http://personas/camiones/internal/{patenteCamion}/finalizar-recorrido", patenteCamion)
                .retrieve()
                .bodyToMono(CamionDto.class)
                .block();
    }

    public void reservarCamion(String patenteCamion) {
        personaWebClient.patch()
                .uri("http://personas/camiones/internal/{patenteCamion}/reservar", patenteCamion)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
