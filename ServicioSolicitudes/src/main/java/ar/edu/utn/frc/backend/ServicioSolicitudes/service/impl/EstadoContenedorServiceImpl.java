package ar.edu.utn.frc.backend.ServicioSolicitudes.service.impl;

import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.EstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.EstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.mapper.EstadoContenedorMapper;
import ar.edu.utn.frc.backend.ServicioSolicitudes.model.EstadoContenedor;
import ar.edu.utn.frc.backend.ServicioSolicitudes.repository.EstadoContenedorRepository;
import ar.edu.utn.frc.backend.ServicioSolicitudes.service.interfaces.IEstadoContenedorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstadoContenedorServiceImpl implements IEstadoContenedorService {

    private final EstadoContenedorRepository estadoContenedorRepository;
    private final EstadoContenedorMapper estadoContenedorMapper;

    @Override
    public EstadoContenedorResponseDto crear(EstadoContenedorRequestDto estadoContenedorRequestDto) {
        EstadoContenedor estadoContenedor = estadoContenedorMapper.toEntity(estadoContenedorRequestDto);
        estadoContenedorRepository.save(estadoContenedor);
        return estadoContenedorMapper.toResponse(estadoContenedor);
    }

    @Override
    public EstadoContenedorResponseDto actualizar(
            Long idEstadoContenedor, EstadoContenedorRequestDto estadoContenedorRequestDto) {

        EstadoContenedor estadoContenedor = estadoContenedorRepository.findById(idEstadoContenedor)
                .orElseThrow(() -> {
                    log.error("Estado Contenedor {} no encontrado", idEstadoContenedor);
                    return new RuntimeException();
                });

        estadoContenedor.setCodigo(estadoContenedorRequestDto.getCodigo());
        estadoContenedor.setDescripcion(estadoContenedorRequestDto.getDescripcion());

        estadoContenedorRepository.save(estadoContenedor);

        return estadoContenedorMapper.toResponse(estadoContenedor);
    }

    @Override
    public void eliminar(Long idEstadoContenedor) {
        EstadoContenedor estadoContenedor = estadoContenedorRepository.findById(idEstadoContenedor)
                .orElseThrow(() -> {
                    log.error("Estado Contenedor {} no encontrado", idEstadoContenedor);
                    return new RuntimeException();
                });
        estadoContenedorRepository.delete(estadoContenedor);
    }

    @Override
    public EstadoContenedorResponseDto obtenerPorId(Long idEstadoContenedor) {
        EstadoContenedor estadoContenedor = estadoContenedorRepository.findById(idEstadoContenedor)
                .orElseThrow(() -> {
                    log.error("Estado Contenedor {} no encontrado", idEstadoContenedor);
                    return new RuntimeException();
                });
        return estadoContenedorMapper.toResponse(estadoContenedor);
    }

    @Override
    public List<EstadoContenedorResponseDto> obtenerTodos() {
        List<EstadoContenedor> estadosContenedor = estadoContenedorRepository.findAll();
        return estadosContenedor.stream()
                .map(estadoContenedorMapper::toResponse)
                .toList();
    }
}
