package ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces;

import ar.edu.utn.frc.backend.ServicioDepositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.service.base.CrudService;

public interface IUbicacionService extends CrudService<UbicacionResponseDto, UbicacionRequestDto, Long> {
    
}
