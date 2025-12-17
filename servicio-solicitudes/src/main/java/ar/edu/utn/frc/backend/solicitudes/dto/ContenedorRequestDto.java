package ar.edu.utn.frc.backend.solicitudes.dto;

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
