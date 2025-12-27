package ar.edu.utn.frc.backend.solicitudes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.solicitudes.dto.CreateSolicitudDto;
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

    @Override
    public void crear(CreateSolicitudDto solicitudRequestDto) {

        // Crea, si no existen, las ubicaciones de origen y destino

        // Crea el contenedor
        double ancho = solicitudRequestDto.getAnchoContenedor();
        double largo = solicitudRequestDto.getLargoContenedor();
        double altura = solicitudRequestDto.getAlturaContenedor();
        double peso = solicitudRequestDto.getPesoContenedor();
        Contenedor contenedor = contenedorService.crear(ancho, largo, altura, peso);

        // Registra, si no esta registrado, el cliente
        //Cliente cliente = null;

        // Busca el Estado inicial
        EstadoSolicitud estadoSolicitud = estadoSolicitudService.buscarPorCodigo("BORRADOR");

        // Crea la solicitud seteando Contenendor, Cliente, Estado y Fecha de Inicio
        Solicitud solicitud = Solicitud.builder()
                .contenedor(contenedor)
                //.cliente(cliente)
                .estadoSolicitud(estadoSolicitud)
                .fechaHoraInicio(LocalDateTime.now())
                .build();

        solicitudRepository.save(solicitud);
    }

    @Override
    public void actualizarParcial(Long idSolicitud, PatchSolicitudDto solicitudRequestDto) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> {
                    log.error("Solicitud {} no encontrada", idSolicitud);
                    return new RuntimeException();
                });
        // Mapea los campos simples no nulos
        solicitudMapper.updateFromPatchDto(solicitudRequestDto, solicitud);

        // Busca el estado si se actualiza
        EstadoSolicitud nuevoEstado = estadoSolicitudService
                .buscarPorCodigo(solicitudRequestDto.getCodigoEstadoSolicitud());
        solicitud.setEstadoSolicitud(nuevoEstado);

        // Busca la ruta si se actualiza
        //Ruta nuevaRuta = null;
        //solicitud.setRuta(nuevaRuta);

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

        return responseDto;
    }

    @Override
    public List<SolicitudResponseDto> obtenerTodos() {
        List<Solicitud> solicitudes = solicitudRepository.findAll();
        return solicitudMapper.toResponseList(solicitudes);
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
}
