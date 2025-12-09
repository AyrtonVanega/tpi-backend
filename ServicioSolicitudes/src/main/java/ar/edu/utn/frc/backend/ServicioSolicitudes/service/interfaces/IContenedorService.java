package ar.edu.utn.frc.backend.ServicioSolicitudes.service.interfaces;

import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.ContenedorRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.ContenedorResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.service.base.CrudService;

public interface IContenedorService extends CrudService<ContenedorResponseDto, ContenedorRequestDto, Long> {
}
