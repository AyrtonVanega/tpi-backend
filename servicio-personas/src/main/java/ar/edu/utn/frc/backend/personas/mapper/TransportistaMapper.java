package ar.edu.utn.frc.backend.personas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.personas.dto.TransportistaRequestDto;
import ar.edu.utn.frc.backend.personas.dto.TransportistaResponseDto;
import ar.edu.utn.frc.backend.personas.model.Transportista;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TransportistaMapper {

    @Mapping(target = "idPersona", ignore = true)
    Transportista toEntity(TransportistaRequestDto dto);

    @Mapping(target = "docTransportista", ignore = true)
    @Mapping(target = "tipoDocTransportista", ignore = true)
    @Mapping(target = "patenteCamion", ignore = true)
    TransportistaResponseDto toResponse(Transportista entity);

    @Mapping(target = "idPersona", ignore = true)
    void updateFromDto(TransportistaRequestDto dto, @MappingTarget Transportista entity);

    List<TransportistaResponseDto> toResponseList(List<Transportista> transportistas);
}
