package ar.edu.utn.frc.backend.rutas.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostoTramoResponseDto {
    private int orden;
    private String patenteCamion;
    private List<DetalleCostoTramoDto> detallesCostoTramo;
}
