package ar.edu.utn.frc.backend.rutas.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchAsignarRutadDto {
    private Long idRuta;
    private double costoEstimado;
    private double tiempoEstimado;
}
