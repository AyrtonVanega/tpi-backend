package ar.edu.utn.frc.backend.personas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamionResponseDto {
    private String patente;
    private double volumen;
    private double peso;
    private boolean disponibilidad;
    private double costoBaseKm;
    private double consumoCombustiblePromedio;
    private String docTransportista;
    private String tipoDocTransportista;
}
