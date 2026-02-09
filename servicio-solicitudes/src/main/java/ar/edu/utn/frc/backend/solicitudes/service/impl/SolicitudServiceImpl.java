package ar.edu.utn.frc.backend.solicitudes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.solicitudes.dto.FinalizarSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PatchAsignarRutadDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PatchSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.exception.BusinessException;
import ar.edu.utn.frc.backend.solicitudes.exception.ResourceNotFoundException;
import ar.edu.utn.frc.backend.solicitudes.mapper.SolicitudMapper;
import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;
import ar.edu.utn.frc.backend.solicitudes.model.EstadoSolicitud;
import ar.edu.utn.frc.backend.solicitudes.model.Solicitud;
import ar.edu.utn.frc.backend.solicitudes.repository.SolicitudRepository;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IContenedorService;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IEstadoSolicitudService;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.ISolicitudService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitudServiceImpl implements ISolicitudService {

    private final IContenedorService contenedorService;
    private final IEstadoSolicitudService estadoSolicitudService;
    private final SolicitudRepository solicitudRepository;
    private final SolicitudMapper solicitudMapper;

    @Override
    public void crear(Long idOrigen, Long idDestino, Contenedor contenedor, String docCliente, String tipoDocCliente) {

        EstadoSolicitud estadoSolicitud = estadoSolicitudService.buscarPorCodigo("BORRADOR");

        Solicitud solicitud = Solicitud.builder()
                .idUbicacionOrigen(idOrigen)
                .idUbicacionDestino(idDestino)
                .contenedor(contenedor)
                .docCliente(docCliente)
                .tipoDocCliente(tipoDocCliente)
                .estadoSolicitud(estadoSolicitud)
                .fechaHoraInicio(LocalDateTime.now())
                .build();

        solicitudRepository.save(solicitud);

        log.info("Solicitud {} creada con estado BORRADOR para el cliente {} {}", solicitud.getIdSolicitud(),
                tipoDocCliente, docCliente);
    }

    @Override
    public void actualizarEstado(Long idSolicitud, PatchSolicitudDto dto) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Solicitud " + idSolicitud + " no encontrada");
                });

        log.info("Actualizando estado de la solicitud {} al estado {}", idSolicitud, dto.getCodigoEstadoSolicitud());

        // Actualiza el estado
        EstadoSolicitud nuevoEstado = estadoSolicitudService.buscarPorCodigo(dto.getCodigoEstadoSolicitud());
        solicitud.setEstadoSolicitud(nuevoEstado);

        solicitudRepository.save(solicitud);
    }

    @Override
    public SolicitudResponseDto obtenerPorId(Long idSolicitud) {
        // Busca la Solicitud en la BD
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Solicitud " + idSolicitud + " no encontrada");
                });

        // Mapea datos simples Entity -> DTO
        SolicitudResponseDto responseDto = solicitudMapper.toResponse(solicitud);

        // Setea el id del contenedor correspondiente al ResponseDto
        responseDto.setIdContenedor(solicitud.getContenedor().getIdContenedor());

        // Setea el estado de la Solicitud al ResponseDto
        responseDto.setCodigoEstadoSolicitud(solicitud.getEstadoSolicitud().getCodigo());
        responseDto.setDescripcionEstadoSolicitud(solicitud.getEstadoSolicitud().getDescripcion());

        // Setea el cliente
        responseDto.setDocCliente(solicitud.getDocCliente());
        responseDto.setTipoDocCliente(solicitud.getTipoDocCliente());

        return responseDto;
    }

    @Override
    public List<SolicitudResponseDto> obtenerTodos() {
        // Obtiene todas las Solicitudes de la BD
        List<Solicitud> solicitudes = solicitudRepository.findAll();

        // Mapea datos simples Entity -> DTO
        List<SolicitudResponseDto> responseDtoList = solicitudMapper.toResponseList(solicitudes);

        // Completa datos faltantes en cada DTo
        for (int i = 0; i < solicitudes.size(); i++) {
            Solicitud solicitud = solicitudes.get(i);
            SolicitudResponseDto dto = responseDtoList.get(i);

            // Setea el id del Contenedor al ResponseDto
            dto.setIdContenedor(solicitud.getContenedor().getIdContenedor());

            // Setea el estado de la Solicitud al ResponseDto
            dto.setCodigoEstadoSolicitud(solicitud.getEstadoSolicitud().getCodigo());
            dto.setDescripcionEstadoSolicitud(solicitud.getEstadoSolicitud().getDescripcion());

            // Setea el cliente
            dto.setDocCliente(solicitud.getDocCliente());
            dto.setTipoDocCliente(solicitud.getTipoDocCliente());
        }

        return responseDtoList;
    }

    @Override
    public void cancelarSolicitud(Long idSolicitud) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Solicitud " + idSolicitud + " no encontrada");
                });

        if (!solicitud.getEstadoSolicitud().getCodigo().equals("BORRADOR")) {
            log.warn("Intento de cancelar la solicitud {} en estado {}", idSolicitud,
                    solicitud.getEstadoSolicitud().getCodigo());
            throw new BusinessException("Sólo se pueden cancelar solicitudes en estado BORRADOR");
        }

        EstadoSolicitud estadoCancelado = estadoSolicitudService.buscarPorCodigo("CANCELADA");
        solicitud.setEstadoSolicitud(estadoCancelado);

        solicitud.setFechaHoraFin(LocalDateTime.now());

        log.info("Solicitud {} cancelada", idSolicitud);

        solicitudRepository.save(solicitud);
    }

    @Override
    public void asignarRuta(Long idSolicitud, PatchAsignarRutadDto asignarRutadDto) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Solicitud " + idSolicitud + " no encontrada");
                });

        solicitud.setIdRuta(asignarRutadDto.getIdRuta());
        solicitud.setCostoEstimado(asignarRutadDto.getCostoEstimado());
        solicitud.setTiempoEstimado(asignarRutadDto.getTiempoEstimado());

        EstadoSolicitud estado = estadoSolicitudService.buscarPorCodigo("PROGRAMADA");
        solicitud.setEstadoSolicitud(estado);

        log.info("Solicitud {} asignada a ruta {}", idSolicitud, asignarRutadDto.getIdRuta());

        solicitudRepository.save(solicitud);
    }

    @Override
    public void finalizarSolicitud(Long idSolicitud, FinalizarSolicitudDto dto) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Solicitud " + idSolicitud + " no encontrada");
                });

        solicitud.setFechaHoraFin(dto.getFechaHoraFin());
        solicitud.setCostoReal(dto.getCostoReal());
        solicitud.setTiempoReal(dto.getTiempoReal());

        EstadoSolicitud estado = estadoSolicitudService.buscarPorCodigo("ENTREGADA");
        solicitud.setEstadoSolicitud(estado);

        contenedorService.finalizarContenedor(solicitud.getContenedor());

        log.info("Solicitud {} finalizada", idSolicitud);

        solicitudRepository.save(solicitud);
    }
}
