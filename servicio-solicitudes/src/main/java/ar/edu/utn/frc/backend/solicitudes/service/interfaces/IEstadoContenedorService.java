package ar.edu.utn.frc.backend.solicitudes.service.interfaces;

import ar.edu.utn.frc.backend.solicitudes.dto.EstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.EstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.service.base.CrudService;

public interface IEstadoContenedorService extends CrudService<
        EstadoContenedorResponseDto, EstadoContenedorRequestDto, Long> {
}
