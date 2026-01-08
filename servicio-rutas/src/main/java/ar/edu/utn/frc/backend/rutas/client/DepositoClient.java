package ar.edu.utn.frc.backend.rutas.client;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.utn.frc.backend.rutas.client.dto.CreateEstadiaDepositoDto;
import ar.edu.utn.frc.backend.rutas.client.dto.DepositoDto;
import ar.edu.utn.frc.backend.rutas.client.dto.FinalizarEstadiaDto;
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

    public void finalizarEstadia(Long idDeposito, Long idSolicitud, LocalDateTime fechaHoraSalida) {
        depositoWebClient.patch()
                .uri("http://depositos/estadias-deposito/internal/{idDeposito}/{idSolicitud}", idDeposito, idSolicitud)
                .bodyValue(new FinalizarEstadiaDto(fechaHoraSalida))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void crearEstadia(Long idDeposito, Long idSolicitud, LocalDateTime fechaHoraEntrada) {
        depositoWebClient.post()
                .uri("http://depositos/estadias-deposito/internal")
                .bodyValue(new CreateEstadiaDepositoDto(idDeposito, idSolicitud, fechaHoraEntrada))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public Double calcularCostoEstadiaDiario(Long idSolicitud) {
        return depositoWebClient.get()
                .uri("http://depositos/estadias-deposito/internal/costo-total?idSolicitud={idSolicitud}", idSolicitud)
                .retrieve()
                .bodyToMono(Double.class)
                .block();
    }
}
