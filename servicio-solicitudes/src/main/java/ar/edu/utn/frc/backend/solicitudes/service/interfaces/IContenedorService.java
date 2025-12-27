package ar.edu.utn.frc.backend.solicitudes.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PatchContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PutContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;

public interface IContenedorService {

    Contenedor crear(double ancho, double largo, double altura, double peso);

    void actualizar(Long idContenedor, PutContenedorDto contenedorRequestDto);

    ContenedorResponseDto obtenerPorId(Long idContenedor);

    List<ContenedorResponseDto> obtenerTodos();

    void actualizarEstado(Long idContenedor, PatchContenedorDto contenedorRequestDto);
}
