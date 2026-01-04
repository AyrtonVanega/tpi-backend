package ar.edu.utn.frc.backend.rutas.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.utn.frc.backend.rutas.client.dto.PatchAsignarRutadDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitudClient {

    private final WebClient solicitudWebClient;

    public void asignarRuta(Long idSolicitud, Long idRuta, double costoEstimado, double tiempoEstimado) {
        solicitudWebClient.patch()
                .uri("http://solicitudes/solicitudes/internal/{idSolicitud}/asignar-ruta", idSolicitud)
                .bodyValue(new PatchAsignarRutadDto(idRuta, costoEstimado, tiempoEstimado))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
