package ar.edu.utn.frc.backend.solicitudes.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EstadoContenedorResponseDto {
    private Long idEstadoContenedor;
    private String codigo;
    private String descripcion;
    private List<Long> idHistorialesContenedor;
}
