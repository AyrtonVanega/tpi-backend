package ar.edu.utn.frc.backend.solicitudes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.model.Solicitud;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SolicitudMapper {

    @Mapping(target = "codigoEstadoSolicitud", ignore = true)
    @Mapping(target = "idContenedor", ignore = true)
    @Mapping(target = "docCliente", ignore = true)
    @Mapping(target = "tipoDocCliente", ignore = true)
    SolicitudResponseDto toResponse(Solicitud entity);

    List<SolicitudResponseDto> toResponseList(List<Solicitud> solicitudes);
}
