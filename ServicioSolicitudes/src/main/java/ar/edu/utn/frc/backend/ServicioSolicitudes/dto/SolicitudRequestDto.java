package ar.edu.utn.frc.backend.ServicioSolicitudes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudRequestDto {
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private double costoEstimado;
    private double tiempoEstimado;
    private double costoReal;
    private double tiempoReal;
    private Long idEstadoSolicitud;
    private Long idContenedor;
    // ruta y cliente
}
