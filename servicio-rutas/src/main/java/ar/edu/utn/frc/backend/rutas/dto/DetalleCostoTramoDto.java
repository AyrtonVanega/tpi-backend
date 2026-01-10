package ar.edu.utn.frc.backend.rutas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCostoTramoDto {
    private String concepto;
    private double subTotal;
}
