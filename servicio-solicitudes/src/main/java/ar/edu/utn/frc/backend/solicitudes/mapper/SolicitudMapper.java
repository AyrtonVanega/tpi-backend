package ar.edu.utn.frc.backend.solicitudes.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.solicitudes.dto.PatchSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.model.Solicitud;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SolicitudMapper {

    @Mapping(target = "codigoEstadoSolicitud", ignore = true)
    @Mapping(target = "idContenedor", ignore = true)
    @Mapping(target = "idRuta", ignore = true)
    @Mapping(target = "docCliente", ignore = true)
    @Mapping(target = "tipoDocCliente", ignore = true)
    SolicitudResponseDto toResponse(Solicitud entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idSolicitud", ignore = true)
    @Mapping(target = "fechaHoraInicio", ignore = true)
    @Mapping(target = "estadoSolicitud", ignore = true)
    @Mapping(target = "contenedor", ignore = true)
    void updateFromPatchDto(PatchSolicitudDto dto, @MappingTarget Solicitud entity);

    List<SolicitudResponseDto> toResponseList(List<Solicitud> solicitudes);
}
