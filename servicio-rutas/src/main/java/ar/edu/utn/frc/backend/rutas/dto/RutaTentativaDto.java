package ar.edu.utn.frc.backend.rutas.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RutaTentativaDto {
    private int cantidadTramos;
    private int cantidadDepositos;
    private double distanciaTotal;
    private double costoEstimado;
    private double tiempoEstimado;
    private List<TramoTentativoDto> tramos;
}
