package ar.edu.utn.frc.backend.ServicioDepositos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstadoEstadiaDepositoRequestDto {
    private String codigo;
    private String descripcion;
}
