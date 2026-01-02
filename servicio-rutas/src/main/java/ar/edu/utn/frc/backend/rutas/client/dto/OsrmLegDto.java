package ar.edu.utn.frc.backend.rutas.client.dto;

import lombok.Data;

@Data
public class OsrmLegDto {
    private double distance; // metros
    private double duration; // segundos
    private String summary;
}
