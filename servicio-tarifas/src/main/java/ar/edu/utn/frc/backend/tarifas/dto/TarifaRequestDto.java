package ar.edu.utn.frc.backend.tarifas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifaRequestDto {
    private double rangoPesoMin;
    private double rangoPesoMax;
    private double rangoVolumenMin;
    private double rangoVolumenMax;
    private double costoBaseKmVolumen;
}
