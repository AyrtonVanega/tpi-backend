package ar.edu.utn.frc.backend.solicitudes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.mapper.SolicitudMapper;
import ar.edu.utn.frc.backend.solicitudes.model.Solicitud;
import ar.edu.utn.frc.backend.solicitudes.repository.SolicitudRepository;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.ISolicitudService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitudServiceImpl implements ISolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final SolicitudMapper solicitudMapper;

    @Override
    public SolicitudResponseDto crear(SolicitudRequestDto solicitudRequestDto) {
        Solicitud solicitud = solicitudMapper.toEntity(solicitudRequestDto);
        solicitudRepository.save(solicitud);
        return solicitudMapper.toResponse(solicitud);
    }

    @Override
    public SolicitudResponseDto actualizar(Long idSolicitud, SolicitudRequestDto solicitudRequestDto) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> {
                    log.error("Solicitud {} no encontrada", idSolicitud);
                    return new RuntimeException();
                });

        solicitud.setFechaHoraInicio(solicitudRequestDto.getFechaHoraInicio());
        solicitud.setFechaHoraFin(solicitudRequestDto.getFechaHoraFin());
        solicitud.setCostoEstimado(solicitudRequestDto.getCostoEstimado());
        solicitud.setTiempoEstimado(solicitudRequestDto.getTiempoEstimado());
        solicitud.setCostoReal(solicitudRequestDto.getCostoReal());
        solicitud.setTiempoReal(solicitudRequestDto.getTiempoReal());

        solicitudRepository.save(solicitud);

        return solicitudMapper.toResponse(solicitud);
    }

    @Override
    public void eliminar(Long idSolicitud) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> {
                    log.error("Solicitud {} no encontrada", idSolicitud);
                    return new RuntimeException();
                });
        solicitudRepository.delete(solicitud);
    }

    @Override
    public SolicitudResponseDto obtenerPorId(Long idSolicitud) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> {
                    log.error("Solicitud {} no encontrada", idSolicitud);
                    return new RuntimeException();
                });
        return solicitudMapper.toResponse(solicitud);
    }

    @Override
    public List<SolicitudResponseDto> obtenerTodos() {
        List<Solicitud> solicitudes = solicitudRepository.findAll();
        return solicitudes.stream()
                .map(solicitudMapper::toResponse)
                .toList();
    }
}
