package ar.edu.utn.frc.backend.rutas.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamionDto {
    private String patente;
    private double costoBaseKm;
    private double consumoCombustiblePromedio;
}
