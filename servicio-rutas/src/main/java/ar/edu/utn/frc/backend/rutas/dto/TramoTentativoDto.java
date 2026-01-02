package ar.edu.utn.frc.backend.rutas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TramoTentativoDto {
    private int orden;
    private Long idUbicacionOrigen;
    private Long idUbicacionDestino;
    private String tipo;
    private double distancia;
    private double costoEstimado;
}
