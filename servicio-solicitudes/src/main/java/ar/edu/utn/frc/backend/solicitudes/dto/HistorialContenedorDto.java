package ar.edu.utn.frc.backend.solicitudes.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialContenedorDto {
    private String estado;
    private LocalDateTime fechaHora;
}
