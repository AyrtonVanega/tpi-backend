package ar.edu.utn.frc.backend.solicitudes.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeguimientoContenedorDto {
    private Long idContenedor;
    private Long idSolicitud;
    private List<HistorialContenedorDto> historialEstados;
}
