package ar.edu.utn.frc.backend.ServicioSolicitudes.service.impl;

import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.HistorialEstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.HistorialEstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.mapper.HistorialEstadoContenedorMapper;
import ar.edu.utn.frc.backend.ServicioSolicitudes.model.HistorialEstadoContenedor;
import ar.edu.utn.frc.backend.ServicioSolicitudes.repository.HistorialEstadoContenedorRepository;
import ar.edu.utn.frc.backend.ServicioSolicitudes.service.interfaces.IHistorialEstadoContenedorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistorialEstadoContenedorServiceImpl implements IHistorialEstadoContenedorService {

    private final HistorialEstadoContenedorRepository historialEstadoContenedorRepository;
    private final HistorialEstadoContenedorMapper historialEstadoContenedorMapper;

    @Override
    public HistorialEstadoContenedorResponseDto crear(HistorialEstadoContenedorRequestDto dto) {
        HistorialEstadoContenedor historial = historialEstadoContenedorMapper.toEntity(dto);
        historialEstadoContenedorRepository.save(historial);
        return historialEstadoContenedorMapper.toResponse(historial);
    }

    @Override
    public HistorialEstadoContenedorResponseDto actualizar(
            Long idHistorialEstadoContenedor, HistorialEstadoContenedorRequestDto dto) {

        HistorialEstadoContenedor historial = historialEstadoContenedorRepository.findById(idHistorialEstadoContenedor)
                .orElseThrow(() -> {
                    log.error("Historial Estado Contenedor {} no encontrado", idHistorialEstadoContenedor);
                    return new RuntimeException();
                });

        historial.setFechaHora(dto.getFechaHora());
        historialEstadoContenedorRepository.save(historial);
        return historialEstadoContenedorMapper.toResponse(historial);
    }

    @Override
    public void eliminar(Long idHistorialEstadoContenedor) {
        HistorialEstadoContenedor historial = historialEstadoContenedorRepository.findById(idHistorialEstadoContenedor)
                .orElseThrow(() -> {
                    log.error("Historial Estado Contenedor {} no encontrado", idHistorialEstadoContenedor);
                    return new RuntimeException();
                });
        historialEstadoContenedorRepository.delete(historial);
    }

    @Override
    public HistorialEstadoContenedorResponseDto obtenerPorId(Long idHistorialEstadoContenedor) {
        HistorialEstadoContenedor historial = historialEstadoContenedorRepository.findById(idHistorialEstadoContenedor)
                .orElseThrow(() -> {
                    log.error("Historial Estado Contenedor {} no encontrado", idHistorialEstadoContenedor);
                    return new RuntimeException();
                });
        return historialEstadoContenedorMapper.toResponse(historial);
    }

    @Override
    public List<HistorialEstadoContenedorResponseDto> obtenerTodos() {
        List<HistorialEstadoContenedor> historiales = historialEstadoContenedorRepository.findAll();
        return historiales.stream()
                .map(historialEstadoContenedorMapper::toResponse)
                .toList();
    }
}
