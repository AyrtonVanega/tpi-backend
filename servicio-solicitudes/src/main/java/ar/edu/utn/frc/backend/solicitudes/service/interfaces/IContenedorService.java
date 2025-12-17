package ar.edu.utn.frc.backend.solicitudes.service.interfaces;

import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.service.base.CrudService;

public interface IContenedorService extends CrudService<ContenedorResponseDto, ContenedorRequestDto, Long> {
}
