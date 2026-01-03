package ar.edu.utn.frc.backend.rutas.dto;

import lombok.Data;

@Data
public class FiltroRutaDto {
    private Long idUbicacionOrigen;
    private Double latitudOrigen;
    private Double longitudOrigen;
    
    private Long idUbicacionDestino;
    private Double latitudDestino;
    private Double longitudDestino;

    private Double costoKmBase;
    private Double consumoCombustibleAprox;
}
