package ar.edu.utn.frc.backend.ServicioDepositos.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DepositoResponseDto {
    private Long idDeposito;
    private String direccion;
    private double latitud;
    private double longitud;
    private String nombreCiudad;
    private double costoEstadiaDiaria;
}
