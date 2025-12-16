package ar.edu.utn.frc.backend.ServicioPersonas.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CamionResponseDto {
    private String patente;
    private double volumen;
    private double peso;
    private boolean disponibilidad;
    private double costoBaseKm;
    private double consumoCombustiblePromedio;
    private String docTransportista;
    private char tipoDocTransportista;
}
