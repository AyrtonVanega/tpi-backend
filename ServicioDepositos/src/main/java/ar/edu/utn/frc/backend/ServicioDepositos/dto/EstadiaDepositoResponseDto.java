package ar.edu.utn.frc.backend.ServicioDepositos.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EstadiaDepositoResponseDto {
    //private id
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    //private estado
}
