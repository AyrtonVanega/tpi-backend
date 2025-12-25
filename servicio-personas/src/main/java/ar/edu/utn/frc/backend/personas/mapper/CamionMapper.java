package ar.edu.utn.frc.backend.personas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.personas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.personas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.personas.model.Camion;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CamionMapper {

    @Mapping(target = "disponibilidad", ignore = true)
    @Mapping(target = "transportista", ignore = true)
    Camion toEntity(CamionRequestDto dto);

    @Mapping(target = "docTransportista", ignore = true)
    @Mapping(target = "tipoDocTransportista", ignore = true)
    CamionResponseDto toResponse(Camion entity);

    @Mapping(target = "disponibilidad", ignore = true)
    @Mapping(target = "transportista", ignore = true)
    void updateFromPutDto(CamionRequestDto dto, @MappingTarget Camion entity);

    List<CamionResponseDto> toResponseList(List<Camion> camiones);
}
