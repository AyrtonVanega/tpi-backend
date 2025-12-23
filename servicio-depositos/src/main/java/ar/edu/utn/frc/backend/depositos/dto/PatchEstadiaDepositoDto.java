package ar.edu.utn.frc.backend.depositos.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchEstadiaDepositoDto {
    private LocalDateTime fechaHoraSalida;
    private String codigoEstado;
}
