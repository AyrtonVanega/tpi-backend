package ar.edu.utn.frc.backend.tarifas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParametroRequestDto {
    private double valorLitroCombustible;
    private double costoGestionBase;
}
