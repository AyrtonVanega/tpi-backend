package ar.edu.utn.frc.backend.personas.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamionActualizadoEvent {
    private String patente;
    private double peso;
    private double volumen;
    private double consumoCombustiblePromedio;
}
