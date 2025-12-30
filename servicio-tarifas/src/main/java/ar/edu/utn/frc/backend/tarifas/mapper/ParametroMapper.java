package ar.edu.utn.frc.backend.tarifas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.tarifas.dto.ParametroRequestDto;
import ar.edu.utn.frc.backend.tarifas.dto.ParametroResponseDto;
import ar.edu.utn.frc.backend.tarifas.model.ParametroGlobal;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ParametroMapper {

    ParametroResponseDto toResponse(ParametroGlobal entity);

    @Mapping(target = "idParametro", ignore = true)
    @Mapping(target = "ultimaModificacion", ignore = true)
    void updateFromDto(ParametroRequestDto dto, @MappingTarget ParametroGlobal entity);
}
