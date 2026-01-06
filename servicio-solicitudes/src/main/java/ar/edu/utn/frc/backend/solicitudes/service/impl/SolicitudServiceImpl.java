package ar.edu.utn.frc.backend.solicitudes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.solicitudes.client.DepositoClient;
import ar.edu.utn.frc.backend.solicitudes.client.PersonaClient;
import ar.edu.utn.frc.backend.solicitudes.client.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.solicitudes.dto.CreateSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.FinalizarSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PatchAsignarRutadDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PatchSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudResponseDto;
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

    private final IEstadoSolicitudService estadoSolicitudService;
    private final IContenedorService contenedorService;

    private final SolicitudRepository solicitudRepository;

    private final SolicitudMapper solicitudMapper;

    private final DepositoClient depositoClient;
    private final PersonaClient personaClient;

    @Override
    public void crear(CreateSolicitudDto solicitudRequestDto) {

        // Crea, si no existen, las ubicaciones de origen y destino
        UbicacionResponseDto origen = depositoClient.crearUbicacion(
                solicitudRequestDto.getDireccionUbicacionOrigen(),
                solicitudRequestDto.getLatitudUbicacionOrigen(),
                solicitudRequestDto.getLongitudUbicacionOrigen(),
                solicitudRequestDto.getNombreCiudadUbicacionOrigen());

        UbicacionResponseDto destino = depositoClient.crearUbicacion(
                solicitudRequestDto.getDireccionUbicacionDestino(),
                solicitudRequestDto.getLatitudUbicacionDestino(),
                solicitudRequestDto.getLongitudUbicacionDestino(),
                solicitudRequestDto.getNombreCiudadUbicacionDestino());

        // Crea el contenedor
        Contenedor contenedor = contenedorService.crear(
                solicitudRequestDto.getAnchoContenedor(),
                solicitudRequestDto.getLargoContenedor(),
                solicitudRequestDto.getAlturaContenedor(),
                solicitudRequestDto.getPesoContenedor());

        // Registra, si no esta registrado, el cliente
        personaClient.registrarCliente(
                solicitudRequestDto.getDocCliente(),
                solicitudRequestDto.getTipoDocCliente(),
                solicitudRequestDto.getNombreCliente(),
                solicitudRequestDto.getApellidoCliente(),
                solicitudRequestDto.getTelefonoCliente(),
                solicitudRequestDto.getEmailCliente());

        // Busca el Estado inicial
        EstadoSolicitud estadoSolicitud = estadoSolicitudService.buscarPorCodigo("BORRADOR");

        // Crea la solicitud seteando Ubicaciones de origen y destino, Contenendor,
        // Cliente, Estado y Fecha de Inicio
        Solicitud solicitud = Solicitud.builder()
                .idUbicacionOrigen(origen.getIdUbicacion())
                .idUbicacionDestino(destino.getIdUbicacion())
                .contenedor(contenedor)
                .docCliente(solicitudRequestDto.getDocCliente())
                .tipoDocCliente(solicitudRequestDto.getTipoDocCliente())
                .estadoSolicitud(estadoSolicitud)
                .fechaHoraInicio(LocalDateTime.now())
                .build();

        solicitudRepository.save(solicitud);
    }

    @Override
    public void actualizarEstado(Long idSolicitud, PatchSolicitudDto dto) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> {
                    log.error("Solicitud {} no encontrada", idSolicitud);
                    return new RuntimeException();
                });

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
                    log.error("Solicitud {} no encontrada", idSolicitud);
                    return new RuntimeException();
                });

        // Mapea datos simples Entity -> DTO
        SolicitudResponseDto responseDto = solicitudMapper.toResponse(solicitud);

        // Setea el id del contenedor correspondiente al ResponseDto
        responseDto.setIdContenedor(solicitud.getContenedor().getIdContenedor());

        // Setea el estado de la Solicitud al ResponseDto
        responseDto.setCodigoEstadoSolicitud(solicitud.getEstadoSolicitud().getCodigo());

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
                    log.error("Solicitud {} no encontrada", idSolicitud);
                    return new RuntimeException();
                });
        
        EstadoSolicitud estadoCancelado = estadoSolicitudService.buscarPorCodigo("CANCELADA");
        solicitud.setEstadoSolicitud(estadoCancelado);

        solicitudRepository.save(solicitud);
    }

    @Override
    public void asignarRuta(Long idSolicitud, PatchAsignarRutadDto asignarRutadDto) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> {
                    log.error("Solicitud {} no encontrada", idSolicitud);
                    return new RuntimeException();
                });

        solicitud.setIdRuta(asignarRutadDto.getIdRuta());
        solicitud.setCostoEstimado(asignarRutadDto.getCostoEstimado());
        solicitud.setTiempoEstimado(asignarRutadDto.getTiempoEstimado());

        EstadoSolicitud estado = estadoSolicitudService.buscarPorCodigo("PROGRAMADA");
        solicitud.setEstadoSolicitud(estado);

        solicitudRepository.save(solicitud);
    }

    @Override
    public void finalizarSolicitud(Long idSolicitud, FinalizarSolicitudDto dto) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> {
                    log.error("Solicitud {} no encontrada", idSolicitud);
                    return new RuntimeException();
                });
        
        solicitud.setFechaHoraFin(dto.getFechaHoraFin());
        solicitud.setCostoReal(dto.getCostoReal());
        solicitud.setTiempoReal(dto.getTiempoReal());

        EstadoSolicitud estado = estadoSolicitudService.buscarPorCodigo("ENTREGADA");
        solicitud.setEstadoSolicitud(estado);

        solicitudRepository.save(solicitud);
    }
}
