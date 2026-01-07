package ar.edu.utn.frc.backend.rutas.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.utn.frc.backend.rutas.client.dto.CamionDto;
import ar.edu.utn.frc.backend.rutas.client.dto.PatchDisponiblidadCamion;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonaClient {

    private final WebClient personaWebClient;

    public void actualizarDisponibilidadCamion(String patenteCamion, boolean disponibilidad) {
        personaWebClient.patch()
                .uri("http://personas/camiones/internal/{patenteCamion}/disponibilidad", patenteCamion)
                .bodyValue(new PatchDisponiblidadCamion(disponibilidad))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public CamionDto obtenerCamionPorId(String patenteCamion) {
        return personaWebClient.get()
                .uri("http://personas/camiones/{patenteCamion}", patenteCamion)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CamionDto>() {
                })
                .block();
    }
}
