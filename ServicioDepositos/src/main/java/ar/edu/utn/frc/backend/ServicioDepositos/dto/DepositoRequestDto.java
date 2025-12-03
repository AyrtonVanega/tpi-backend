package ar.edu.utn.frc.backend.ServicioDepositos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositoRequestDto {
    private String direccion;
    private double latitud;
    private double longitud;
    private String nombreCiudad;
    private double costoEstadiaDiaria;
}
