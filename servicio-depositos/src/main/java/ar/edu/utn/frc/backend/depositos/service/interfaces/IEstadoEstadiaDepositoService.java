package ar.edu.utn.frc.backend.depositos.service.interfaces;

import ar.edu.utn.frc.backend.depositos.dto.EstadoEstadiaDepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.EstadoEstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.service.base.CrudService;

public interface IEstadoEstadiaDepositoService extends CrudService<
        EstadoEstadiaDepositoResponseDto, EstadoEstadiaDepositoRequestDto, Long> {
    
}
