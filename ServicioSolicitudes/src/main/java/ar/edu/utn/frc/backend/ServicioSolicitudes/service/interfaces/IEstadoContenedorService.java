package ar.edu.utn.frc.backend.ServicioSolicitudes.service.interfaces;

import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.EstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.EstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.service.base.CrudService;

public interface IEstadoContenedorService extends CrudService<
        EstadoContenedorResponseDto, EstadoContenedorRequestDto, Long> {
}
