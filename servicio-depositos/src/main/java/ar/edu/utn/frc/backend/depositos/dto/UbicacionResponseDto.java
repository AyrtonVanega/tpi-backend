package ar.edu.utn.frc.backend.depositos.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UbicacionResponseDto {
    private Long idUbicacion;
    private String direccion;
    private double latitud;
    private double longitud;
    private String nombreCiudad;
}
