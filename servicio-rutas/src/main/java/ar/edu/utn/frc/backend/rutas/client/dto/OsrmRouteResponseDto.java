package ar.edu.utn.frc.backend.rutas.client.dto;

import java.util.List;

import lombok.Data;

@Data
public class OsrmRouteResponseDto {
    private List<OsrmRouteDto> routes;
    private String code;
}
