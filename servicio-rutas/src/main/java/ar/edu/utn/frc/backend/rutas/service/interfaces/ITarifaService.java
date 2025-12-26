package ar.edu.utn.frc.backend.rutas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.rutas.dto.TarifaRequestDto;
import ar.edu.utn.frc.backend.rutas.dto.TarifaResponseDto;

public interface ITarifaService {
    
    void crear(TarifaRequestDto dto);

    void actualizar(Long idTarifa, TarifaRequestDto dto);

    void eliminar(Long idTarifa);

    TarifaResponseDto obtenerPorId(Long idTarifa);

    List<TarifaResponseDto> obtenerTodos();
}
