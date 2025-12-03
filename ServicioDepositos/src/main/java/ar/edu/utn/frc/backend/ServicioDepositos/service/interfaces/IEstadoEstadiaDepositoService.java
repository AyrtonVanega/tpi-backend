package ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces;

import ar.edu.utn.frc.backend.ServicioDepositos.dto.EstadoEstadiaDepositoRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.EstadoEstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.service.base.CrudService;

public interface IEstadoEstadiaDepositoService extends CrudService<
        EstadoEstadiaDepositoResponseDto, EstadoEstadiaDepositoRequestDto, Long> {
    
}
