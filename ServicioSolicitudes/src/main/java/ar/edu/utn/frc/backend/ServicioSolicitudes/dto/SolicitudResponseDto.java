package ar.edu.utn.frc.backend.ServicioSolicitudes.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SolicitudResponseDto {
    private Long idSolicitud;
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
