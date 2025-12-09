package ar.edu.utn.frc.backend.ServicioSolicitudes.service.impl;

import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.SolicitudRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.mapper.SolicitudMapper;
import ar.edu.utn.frc.backend.ServicioSolicitudes.model.Solicitud;
import ar.edu.utn.frc.backend.ServicioSolicitudes.repository.SolicitudRepository;
import ar.edu.utn.frc.backend.ServicioSolicitudes.service.interfaces.ISolicitudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
