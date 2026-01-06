package ar.edu.utn.frc.backend.solicitudes.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.solicitudes.dto.CreateSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PatchAsignarRutadDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PatchSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudResponseDto;

public interface ISolicitudService {

    void crear(CreateSolicitudDto solicitudRequestDto);

    void actualizarEstado(Long idSolicitud, PatchSolicitudDto solicitudRequestDto);

    SolicitudResponseDto obtenerPorId(Long idSolicitud);

    List<SolicitudResponseDto> obtenerTodos();

    void cancelarSolicitud(Long idSolicitud);

    void asignarRuta(Long idSolicitud, PatchAsignarRutadDto asignarRutadDto);
}
