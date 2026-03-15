package ar.edu.utn.frc.backend.depositos.event;

import ar.edu.utn.frc.backend.depositos.dto.UbicacionRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudCreadaDepositoEvent {
    private UbicacionRequestDto origen;
    private UbicacionRequestDto destino;
}
