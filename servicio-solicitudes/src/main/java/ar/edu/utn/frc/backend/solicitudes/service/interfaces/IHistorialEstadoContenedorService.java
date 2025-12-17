package ar.edu.utn.frc.backend.solicitudes.service.interfaces;

import ar.edu.utn.frc.backend.solicitudes.dto.HistorialEstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.HistorialEstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.service.base.CrudService;

public interface IHistorialEstadoContenedorService extends CrudService<
        HistorialEstadoContenedorResponseDto, HistorialEstadoContenedorRequestDto, Long> {
}
