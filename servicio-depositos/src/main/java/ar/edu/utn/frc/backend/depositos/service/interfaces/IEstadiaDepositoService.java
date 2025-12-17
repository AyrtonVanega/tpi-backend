package ar.edu.utn.frc.backend.depositos.service.interfaces;

import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.model.EstadiaDepositoId;
import ar.edu.utn.frc.backend.depositos.service.base.CrudService;

public interface IEstadiaDepositoService extends CrudService<
        EstadiaDepositoResponseDto, EstadiaDepositoRequestDto, EstadiaDepositoId> {
    
}
