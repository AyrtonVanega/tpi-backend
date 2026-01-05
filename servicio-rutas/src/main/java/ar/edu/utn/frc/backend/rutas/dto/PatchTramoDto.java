package ar.edu.utn.frc.backend.rutas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchTramoDto {
    private String patenteCamion;
    private double volumenCamion;
    private double pesoCamion;
    private boolean disponibilidad;

    private double volumenContenedor;
    private double pesoContenedor;
}
