package ar.edu.utn.frc.backend.solicitudes.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.solicitudes.dto.FinalizarSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PatchAsignarRutadDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PatchSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;

public interface ISolicitudService {

    void crear(Long idOrigen, Long idDestino, Contenedor contenedor, String docCliente, String tipoDocCliente);

    void actualizarEstado(Long idSolicitud, PatchSolicitudDto solicitudRequestDto);

    SolicitudResponseDto obtenerPorId(Long idSolicitud);

    List<SolicitudResponseDto> obtenerTodos();

    void cancelarSolicitud(Long idSolicitud);

    void asignarRuta(Long idSolicitud, PatchAsignarRutadDto asignarRutadDto);

    void finalizarSolicitud(Long idSolicitud, FinalizarSolicitudDto dto);
}
