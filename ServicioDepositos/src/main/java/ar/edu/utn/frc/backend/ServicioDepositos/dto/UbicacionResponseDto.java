package ar.edu.utn.frc.backend.ServicioDepositos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionResponseDto {
    private Long idUbicacion;
    private String direccion;
    private double latitud;
    private double longitud;
    private String nombreCiudad;
}
