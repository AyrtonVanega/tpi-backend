package ar.edu.utn.frc.backend.solicitudes.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EstadoSolicitudResponseDto {
    private Long idEstadoSolicitud;
    private String codigo;
    private String descripcion;
    private List<Long> idSolicitudes;
}
