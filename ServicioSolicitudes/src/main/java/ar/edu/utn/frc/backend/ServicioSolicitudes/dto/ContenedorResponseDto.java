package ar.edu.utn.frc.backend.ServicioSolicitudes.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContenedorResponseDto {
    private Long idContenedor;
    private double ancho;
    private double alto;
    private double largo;
    private double peso;
    private Long idSolicitud;
}
