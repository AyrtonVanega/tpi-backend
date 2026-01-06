package ar.edu.utn.frc.backend.rutas.client.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEstadiaDepositoDto {
    private Long idDeposito;
    private Long idSolicitud;
    private LocalDateTime fechaHoraEntrada;
}
