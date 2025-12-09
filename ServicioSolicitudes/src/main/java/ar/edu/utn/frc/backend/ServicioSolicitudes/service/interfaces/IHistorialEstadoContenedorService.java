package ar.edu.utn.frc.backend.ServicioSolicitudes.service.interfaces;

import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.HistorialEstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.HistorialEstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.service.base.CrudService;

public interface IHistorialEstadoContenedorService extends CrudService<
        HistorialEstadoContenedorResponseDto, HistorialEstadoContenedorRequestDto, Long> {
}
