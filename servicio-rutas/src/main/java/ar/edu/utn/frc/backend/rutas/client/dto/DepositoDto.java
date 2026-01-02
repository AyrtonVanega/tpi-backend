package ar.edu.utn.frc.backend.rutas.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositoDto {
    private Long idDeposito;
    private String direccion;
    private double latitud;
    private double longitud;
    private String nombreCiudad;
    private double costoEstadiaDiaria;
}
