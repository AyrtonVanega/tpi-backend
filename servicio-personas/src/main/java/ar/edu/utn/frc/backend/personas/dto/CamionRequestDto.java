package ar.edu.utn.frc.backend.personas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamionRequestDto {
    private double volumen;
    private double peso;
    private double costoBaseKm;
    private double consumoCombustiblePromedio;
}
