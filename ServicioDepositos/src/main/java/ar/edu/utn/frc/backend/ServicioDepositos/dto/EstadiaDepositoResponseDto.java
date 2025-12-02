package ar.edu.utn.frc.backend.ServicioDepositos.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstadiaDepositoResponseDto {
    //private id
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    //private estado
}
