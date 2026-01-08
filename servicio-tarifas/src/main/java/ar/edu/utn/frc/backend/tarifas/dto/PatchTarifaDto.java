package ar.edu.utn.frc.backend.tarifas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchTarifaDto {
    private Double costoBaseKmVolumen;
    private Double consumoCombustibleGralAprox;
}
