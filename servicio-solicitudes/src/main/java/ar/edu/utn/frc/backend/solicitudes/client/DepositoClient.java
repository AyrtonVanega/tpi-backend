package ar.edu.utn.frc.backend.solicitudes.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.utn.frc.backend.solicitudes.client.dto.UbicacionRequestDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositoClient {

    private final WebClient depositoWebClient;

    public void crearUbicacion(String direccion, double latitud, double longitud, String nombreCiudad) {
        depositoWebClient.post()
                .uri("http://depositos/ubicaciones/internal")
                .bodyValue(new UbicacionRequestDto(direccion, latitud, longitud, nombreCiudad))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
