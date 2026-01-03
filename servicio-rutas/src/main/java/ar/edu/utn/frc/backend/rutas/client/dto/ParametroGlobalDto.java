package ar.edu.utn.frc.backend.rutas.client.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParametroGlobalDto {
    private double valorLitroCombustible;
    private double costoGestionBase;
    private LocalDate ultimaModificacion;
}
