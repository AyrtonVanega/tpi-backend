package ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces;

import ar.edu.utn.frc.backend.ServicioDepositos.dto.EstadiaDepositoRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.EstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.model.EstadiaDepositoId;
import ar.edu.utn.frc.backend.ServicioDepositos.service.base.CrudService;

public interface IEstadiaDepositoService extends CrudService<
        EstadiaDepositoResponseDto, EstadiaDepositoRequestDto, EstadiaDepositoId> {
    
}
