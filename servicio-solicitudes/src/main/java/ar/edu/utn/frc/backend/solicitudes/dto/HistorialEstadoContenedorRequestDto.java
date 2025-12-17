package ar.edu.utn.frc.backend.solicitudes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialEstadoContenedorRequestDto {
    private LocalDateTime fechaHora;
    private Long idContenedor;
    private Long idEstado;
}
