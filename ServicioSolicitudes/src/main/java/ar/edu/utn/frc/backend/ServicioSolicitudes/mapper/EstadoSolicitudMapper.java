package ar.edu.utn.frc.backend.ServicioSolicitudes.mapper;

import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.EstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.EstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.EstadoSolicitudRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.EstadoSolicitudResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.model.EstadoSolicitud;
import org.springframework.stereotype.Component;

@Component
public class EstadoSolicitudMapper implements GenericMapper<
        EstadoSolicitud, EstadoSolicitudResponseDto, EstadoSolicitudRequestDto> {

    @Override
    public EstadoSolicitud toEntity(EstadoSolicitudRequestDto dto) {
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();

        estadoSolicitud.setCodigo(dto.getCodigo());
        estadoSolicitud.setDescripcion(dto.getDescripcion());

        return estadoSolicitud;
    }

    @Override
    public EstadoSolicitudResponseDto toResponse(EstadoSolicitud entity) {
        return EstadoSolicitudResponseDto.builder()
                .idEstadoSolicitud(entity.getIdEstadoSolicitud())
                .codigo(entity.getCodigo())
                .descripcion(entity.getDescripcion())
                .build();
    }
}
