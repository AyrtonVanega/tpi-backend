package ar.edu.utn.frc.backend.solicitudes.mapper;

import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.solicitudes.dto.EstadoSolicitudRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.EstadoSolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.model.EstadoSolicitud;

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
