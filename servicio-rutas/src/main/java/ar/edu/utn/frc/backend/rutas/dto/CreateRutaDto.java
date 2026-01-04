package ar.edu.utn.frc.backend.rutas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRutaDto {
    private Long idSolicitud;
    private RutaTentativaDto rutaSeleccionada;
}
