package ar.edu.utn.frc.backend.ServicioDepositos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UbicacionRequestDto {
    private String direccion;
    private double latitud;
    private double longitud;
    private String nombreCiudad;
}
