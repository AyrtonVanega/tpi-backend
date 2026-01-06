package ar.edu.utn.frc.backend.rutas.client;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.utn.frc.backend.rutas.client.dto.FinalizarSolicitudDto;
import ar.edu.utn.frc.backend.rutas.client.dto.PatchAsignarRutadDto;
import ar.edu.utn.frc.backend.rutas.client.dto.PatchContenedorDto;
import ar.edu.utn.frc.backend.rutas.client.dto.PatchSolicitudDto;
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

    public void actualizarEstadoContenedor(Long idContenedor, String codigoEstadoContenedor) {
        solicitudWebClient.patch()
                .uri("http://solicitudes/contenedores/internal/{idContenedor}/estado", idContenedor)
                .bodyValue(new PatchContenedorDto(codigoEstadoContenedor))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void actualizarEstadoSolicitud(Long idSolicitud, String codigoEstadoSolicitud) {
        solicitudWebClient.patch()
                .uri("http://solicitudes/solicitudes/internal/{idSolicitud}/estado", idSolicitud)
                .bodyValue(new PatchSolicitudDto(codigoEstadoSolicitud))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void finalizarSolicitud(Long idSolicitud, LocalDateTime fechaHoraFin, double tiempoReal, double costoReal) {
        solicitudWebClient.patch()
                .uri("http://solicitudes/solicitudes/internal/{idSolicitud}/finalizar", idSolicitud)
                .bodyValue(new FinalizarSolicitudDto(fechaHoraFin, tiempoReal, costoReal))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
