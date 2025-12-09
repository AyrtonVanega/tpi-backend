package ar.edu.utn.frc.backend.ServicioSolicitudes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoContenedorRequestDto {
    private String codigo;
    private String descripcion;
}
