package ar.edu.utn.frc.backend.tarifas.service.interfaces;

import ar.edu.utn.frc.backend.tarifas.dto.ParametroRequestDto;
import ar.edu.utn.frc.backend.tarifas.dto.ParametroResponseDto;

public interface IParametroGlobalService {
    
    void actualizar(ParametroRequestDto dto);

    ParametroResponseDto obtenerParametroGlobal();
}
