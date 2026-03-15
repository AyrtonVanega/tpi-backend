package ar.edu.utn.frc.backend.solicitudes.event;

import ar.edu.utn.frc.backend.solicitudes.dto.ClienteDto;
import ar.edu.utn.frc.backend.solicitudes.dto.UbicacionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudCreadaEvent {
    private UbicacionDto origen;
    private UbicacionDto destino;
    private ClienteDto cliente;
}
