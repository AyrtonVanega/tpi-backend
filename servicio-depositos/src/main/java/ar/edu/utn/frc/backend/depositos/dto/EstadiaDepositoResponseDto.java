package ar.edu.utn.frc.backend.depositos.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EstadiaDepositoResponseDto {
    private Long idDeposito;
    private Long idSolicitud;
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private EstadoEstadiaDepositoResponseDto estado;
}
