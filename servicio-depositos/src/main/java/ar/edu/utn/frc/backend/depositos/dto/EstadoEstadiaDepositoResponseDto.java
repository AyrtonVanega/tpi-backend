package ar.edu.utn.frc.backend.depositos.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EstadoEstadiaDepositoResponseDto {
    private Long idEstado;
    private String codigo;
    private String descripcion;
}
