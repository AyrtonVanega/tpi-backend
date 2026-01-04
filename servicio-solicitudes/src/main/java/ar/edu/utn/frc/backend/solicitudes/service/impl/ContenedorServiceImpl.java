package ar.edu.utn.frc.backend.solicitudes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.dto.HistorialContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PatchContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PutContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SeguimientoContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.mapper.ContenedorMapper;
import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;
import ar.edu.utn.frc.backend.solicitudes.model.EstadoContenedor;
import ar.edu.utn.frc.backend.solicitudes.model.HistorialEstadoContenedor;
import ar.edu.utn.frc.backend.solicitudes.model.Solicitud;
import ar.edu.utn.frc.backend.solicitudes.repository.ContenedorRepository;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IContenedorService;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IHistorialEstadoContenedorService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContenedorServiceImpl implements IContenedorService {

    private final IHistorialEstadoContenedorService historialEstadoService;

    private final ContenedorRepository contenedorRepository;

    private final ContenedorMapper contenedorMapper;

    @Override
    public Contenedor crear(double ancho, double largo, double altura, double peso) {
        // Crea el contenedor
        Contenedor contenedor = Contenedor.builder()
                .ancho(ancho)
                .largo(largo)
                .alto(altura)
                .peso(peso)
                .build();

        // Guarda el contenedor
        contenedor = contenedorRepository.save(contenedor);

        // Registra el estado inicial en el historial
        historialEstadoService.registarCambioEstado(contenedor, "EN_ORIGEN");

        return contenedor;
    }

    @Override
    public void actualizar(Long idContenedor, PutContenedorDto contenedorRequestDto) {
        Contenedor contenedor = contenedorRepository.findById(idContenedor)
                .orElseThrow(() -> {
                    log.error("Contenedor {} no encontrado", idContenedor);
                    return new RuntimeException();
                });

        contenedorMapper.updateFromPutDto(contenedorRequestDto, contenedor);
        contenedorRepository.save(contenedor);
    }

    @Override
    public ContenedorResponseDto obtenerPorId(Long idContenedor) {
        // Busca el Contenedor en la BD
        Contenedor contenedor = contenedorRepository.findById(idContenedor)
                .orElseThrow(() -> {
                    log.error("Contenedor {} no encontrado", idContenedor);
                    return new RuntimeException();
                });

        // Mapea datos simples Entity -> DTO
        ContenedorResponseDto responseDto = contenedorMapper.toResponse(contenedor);

        // Setea el id de la solicitud al ResponseDto
        Solicitud solicitud = contenedor.getSolicitud();
        if (solicitud != null) {
            responseDto.setIdSolicitud(solicitud.getIdSolicitud());
        }

        // Setea el estadoActual al ResponseDto
        EstadoContenedor estadoActual = historialEstadoService.obtenerEstadoActual(idContenedor);
        responseDto.setEstadoActual(estadoActual.getCodigo());

        return responseDto;
    }

    @Override
    public List<ContenedorResponseDto> obtenerTodos() {
        // Obtiene todos los Contenedores de la BD
        List<Contenedor> contenedores = contenedorRepository.findAll();

        // Mapea datos simples Entity -> DTO
        List<ContenedorResponseDto> responseDtoList = contenedorMapper.toResponseList(contenedores);

        // Completa datos faltantes en cada DTO
        for (int i = 0; i < contenedores.size(); i++) {
            Contenedor contenedor = contenedores.get(i);
            ContenedorResponseDto dto = responseDtoList.get(i);

            // Setea el id de la solicitud al ResponseDto
            Solicitud solicitud = contenedor.getSolicitud();
            if (solicitud != null) {
                dto.setIdSolicitud(solicitud.getIdSolicitud());
            }

            // Setea el estadoActual al ResponseDto
            EstadoContenedor estadoActual = historialEstadoService.obtenerEstadoActual(contenedor.getIdContenedor());
            dto.setEstadoActual(estadoActual.getCodigo());
        }

        return responseDtoList;
    }

    @Override
    public void actualizarEstado(Long idContenedor, PatchContenedorDto contenedorRequestDto) {
        Contenedor contenedor = contenedorRepository.findById(idContenedor)
                .orElseThrow(() -> {
                    log.error("Contenedor {} no encontrado", idContenedor);
                    return new RuntimeException();
                });

        // Registra el cambio de estado en el historial
        historialEstadoService.registarCambioEstado(contenedor, contenedorRequestDto.getCodigoEstadoContenedor());
    }

    @Override
    public SeguimientoContenedorDto obtenerEstadosContenedor(Long idContenedor) {
        Contenedor contenedor = contenedorRepository.findById(idContenedor)
                .orElseThrow(() -> {
                    log.error("Contenedor {} no encontrado", idContenedor);
                    return new RuntimeException();
                });

        SeguimientoContenedorDto estados = new SeguimientoContenedorDto();
        estados.setIdContenedor(contenedor.getIdContenedor());
        estados.setIdSolicitud(contenedor.getSolicitud().getIdSolicitud());

        List<HistorialEstadoContenedor> historiales = contenedor.getHistorialesEstadoContenedor();

        List<HistorialContenedorDto> historialEstadosDto = new ArrayList<>();

        for (int i = 0; i < historiales.size(); i++) {
            HistorialEstadoContenedor historial = historiales.get(i);
            HistorialContenedorDto dto = new HistorialContenedorDto();

            dto.setFechaHora(historial.getFechaHora());
            dto.setEstado(historial.getEstadoContenedor().getCodigo());

            historialEstadosDto.add(dto);
        }

        estados.setHistorialEstados(historialEstadosDto);

        return estados;
    }
}
