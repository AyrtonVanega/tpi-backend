package ar.edu.utn.frc.backend.depositos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionResponseDto {
    private String direccion;
    private double latitud;
    private double longitud;
    private String nombreCiudad;
}
