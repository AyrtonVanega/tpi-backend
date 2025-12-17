package ar.edu.utn.frc.backend.depositos.service.interfaces;

import ar.edu.utn.frc.backend.depositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.depositos.service.base.CrudService;

public interface IUbicacionService extends CrudService<UbicacionResponseDto, UbicacionRequestDto, Long> {
    
}
