package ar.edu.utn.frc.backend.personas.event;

import ar.edu.utn.frc.backend.personas.dto.CreateClienteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudCreadaPersonaEvent {
    private CreateClienteDto cliente;
}
