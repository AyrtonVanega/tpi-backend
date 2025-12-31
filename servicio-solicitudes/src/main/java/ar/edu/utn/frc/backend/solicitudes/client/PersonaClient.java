package ar.edu.utn.frc.backend.solicitudes.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.utn.frc.backend.solicitudes.client.dto.ClienteRequestDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonaClient {

    private final WebClient personaWebClient;

    public void registrarCliente(String docCliente, String tipoDocCliente, String nombre, String apellido,
            String telefono, String email) {

        personaWebClient.post()
                .uri("http://personas/clientes/internal")
                .bodyValue(new ClienteRequestDto(docCliente, tipoDocCliente, nombre, apellido, telefono, email))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
