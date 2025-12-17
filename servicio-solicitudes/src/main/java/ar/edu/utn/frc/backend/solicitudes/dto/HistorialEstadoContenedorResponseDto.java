package ar.edu.utn.frc.backend.solicitudes.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class HistorialEstadoContenedorResponseDto {
    private Long idHistorialEstadoContenedor;
    private LocalDateTime fechaHora;
    private Long idContenedor;
    private Long idEstado;
}
