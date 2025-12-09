package ar.edu.utn.frc.backend.ServicioSolicitudes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContenedorRequestDto {
    private double ancho;
    private double alto;
    private double largo;
    private double peso;
}
