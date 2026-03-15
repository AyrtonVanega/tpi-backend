package ar.edu.utn.frc.backend.solicitudes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSolicitudDto {
    private UbicacionDto origen;
    private UbicacionDto destino;
    private ClienteDto cliente;
    private CreateContenedorDto contenedor;
}
