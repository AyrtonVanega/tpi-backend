package ar.edu.utn.frc.backend.ServicioDepositos.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadiaDepositoRequestDto {
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    //private estado
}
