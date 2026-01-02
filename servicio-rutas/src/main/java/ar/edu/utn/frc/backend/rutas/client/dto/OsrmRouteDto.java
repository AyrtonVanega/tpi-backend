package ar.edu.utn.frc.backend.rutas.client.dto;

import java.util.List;

import lombok.Data;

@Data
public class OsrmRouteDto {
    private double distance; // metros
    private double duration; // segundos
    private List<OsrmLegDto> legs;
}
