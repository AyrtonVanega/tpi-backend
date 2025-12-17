package ar.edu.utn.frc.backend.solicitudes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.solicitudes.dto.EstadoSolicitudRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.EstadoSolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.mapper.EstadoSolicitudMapper;
import ar.edu.utn.frc.backend.solicitudes.model.EstadoSolicitud;
import ar.edu.utn.frc.backend.solicitudes.repository.EstadoSolicitudRepository;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IEstadoSolicitudService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstadoSolicitudServiceImpl implements IEstadoSolicitudService {

    private final EstadoSolicitudRepository estadoSolicitudRepository;
    private final EstadoSolicitudMapper estadoSolicitudMapper;

    @Override
    public EstadoSolicitudResponseDto crear(EstadoSolicitudRequestDto estadoSolicitudRequestDto) {
        EstadoSolicitud estadoSolicitud = estadoSolicitudMapper.toEntity(estadoSolicitudRequestDto);
        estadoSolicitudRepository.save(estadoSolicitud);
        return estadoSolicitudMapper.toResponse(estadoSolicitud);
    }

    @Override
    public EstadoSolicitudResponseDto actualizar(
            Long idEstadoSolicitud, EstadoSolicitudRequestDto estadoSolicitudRequestDto) {

        EstadoSolicitud estadoSolicitud = estadoSolicitudRepository.findById(idEstadoSolicitud)
                .orElseThrow(() -> {
                    log.error("Estado Solicitud {} no encontrado", idEstadoSolicitud);
                    return new RuntimeException();
                });

        estadoSolicitud.setCodigo(estadoSolicitudRequestDto.getCodigo());
        estadoSolicitud.setDescripcion(estadoSolicitudRequestDto.getDescripcion());

        estadoSolicitudRepository.save(estadoSolicitud);

        return estadoSolicitudMapper.toResponse(estadoSolicitud);
    }

    @Override
    public void eliminar(Long idEstadoSolicitud) {
        EstadoSolicitud estadoSolicitud = estadoSolicitudRepository.findById(idEstadoSolicitud)
                .orElseThrow(() -> {
                    log.error("Estado Solicitud {} no encontrado", idEstadoSolicitud);
                    return new RuntimeException();
                });
        estadoSolicitudRepository.delete(estadoSolicitud);
    }

    @Override
    public EstadoSolicitudResponseDto obtenerPorId(Long idEstadoSolicitud) {
        EstadoSolicitud estadoSolicitud = estadoSolicitudRepository.findById(idEstadoSolicitud)
                .orElseThrow(() -> {
                    log.error("Estado Solicitud {} no encontrado", idEstadoSolicitud);
                    return new RuntimeException();
                });
        return estadoSolicitudMapper.toResponse(estadoSolicitud);
    }

    @Override
    public List<EstadoSolicitudResponseDto> obtenerTodos() {
        List<EstadoSolicitud> estadosSolicitud = estadoSolicitudRepository.findAll();
        return estadosSolicitud.stream()
                .map(estadoSolicitudMapper::toResponse)
                .toList();
    }
}
