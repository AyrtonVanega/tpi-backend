package ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces;

import ar.edu.utn.frc.backend.ServicioDepositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.service.base.CrudService;

public interface IDepositoService extends CrudService<DepositoResponseDto, DepositoRequestDto, Long> {
    
}
