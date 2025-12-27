package ar.edu.utn.frc.backend.rutas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifaResponseDto {
    private Long idTarifa;
    private double rangoPesoMin;
    private double rangoPesoMax;
    private double rangoVolumenMin;
    private double rangoVolumenMax;
    private double valorLitroCombustible;
    private double costoGestionBase;
    private double costoBaseKmVolumen;
    private double consumoCombustibleGralAprox;
}
