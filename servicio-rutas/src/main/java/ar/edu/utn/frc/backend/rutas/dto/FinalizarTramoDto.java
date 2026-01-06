package ar.edu.utn.frc.backend.rutas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalizarTramoDto {
    private String patenteCamion;
    private double costoKmBase;
    private double consumoCombustiblePromedio;
    private double valorLitroCombustible;
}
