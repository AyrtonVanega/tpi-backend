package ar.edu.utn.frc.backend.ServicioDepositos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositoResponseDto {
    private Long idDeposito;
    private String direccion;
    private double latitud;
    private double longitud;
    private String nombreCiudad;
    private double costoEstadiaDiaria;
}
