package ar.edu.utn.frc.backend.ServicioDepositos.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstadiaDepositoRequestDto {
    private Long idDeposito;
    private Long idSolicitud;
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private Long idEstado;
}
