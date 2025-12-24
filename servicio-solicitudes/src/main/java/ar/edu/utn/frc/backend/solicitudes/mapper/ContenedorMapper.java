package ar.edu.utn.frc.backend.solicitudes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.dto.CreateContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PutContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ContenedorMapper {

    @Mapping(target = "idContenedor", ignore = true)
    @Mapping(target = "historialesEstadoContenedor", ignore = true)
    Contenedor toEntity(CreateContenedorDto dto);

    @Mapping(target = "idSolicitud", ignore = true)
    ContenedorResponseDto toResponse(Contenedor entity);

    @Mapping(target = "idContenedor", ignore = true)
    @Mapping(target = "historialesEstadoContenedor", ignore = true)
    void updateFromPutDto(PutContenedorDto dto, @MappingTarget Contenedor entity);

    List<ContenedorResponseDto> toResponseList(List<Contenedor> contenedores);
}
