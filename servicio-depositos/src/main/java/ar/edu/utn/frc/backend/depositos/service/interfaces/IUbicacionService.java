package ar.edu.utn.frc.backend.depositos.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.depositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.depositos.model.Ubicacion;

public interface IUbicacionService {
    
    Ubicacion crearSiNoExiste(UbicacionRequestDto dto);

    void actualizar(Long idUbicacion, UbicacionRequestDto dto);

    void eliminar(Long idUbicacion);

    UbicacionResponseDto obtenerPorId(Long idUbicacion);

    List<UbicacionResponseDto> obtenerTodos();
}
