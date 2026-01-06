package ar.edu.utn.frc.backend.rutas.client.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalizarEstadiaDto {
    private LocalDateTime fechaHoraSalida;
}
