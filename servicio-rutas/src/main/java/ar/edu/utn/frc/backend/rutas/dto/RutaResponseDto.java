package ar.edu.utn.frc.backend.rutas.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaResponseDto {
    private Long idRuta;
    private int cantidadTramos;
    private int cantidadDepositos;
    private double distanciaTotal;
    private double costoEstimado;
    private double tiempoEstimado;
    private double costoReal;
    private double tiempoReal;
    private List<TramoResponseDto> tramos;
}
