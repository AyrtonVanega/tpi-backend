package ar.edu.utn.frc.backend.solicitudes.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionRequestDto {
    private String direccion;
    private double latitud;
    private double longitud;
    private String nombreCiudad;
}
