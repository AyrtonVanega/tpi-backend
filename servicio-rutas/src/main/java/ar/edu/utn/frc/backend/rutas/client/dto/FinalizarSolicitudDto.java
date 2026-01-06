package ar.edu.utn.frc.backend.rutas.client.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalizarSolicitudDto {
    private LocalDateTime fechaHoraFin;
    private double tiempoReal;
    private double costoReal;
}
