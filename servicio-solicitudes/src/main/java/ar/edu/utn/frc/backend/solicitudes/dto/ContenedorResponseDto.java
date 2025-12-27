package ar.edu.utn.frc.backend.solicitudes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContenedorResponseDto {
    private Long idContenedor;
    private double ancho;
    private double alto;
    private double largo;
    private double peso;
    private Long idSolicitud;
    private String estadoActual;
}
