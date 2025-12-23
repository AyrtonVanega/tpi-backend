package ar.edu.utn.frc.backend.depositos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.depositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.depositos.model.Ubicacion;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UbicacionMapper {

    @Mapping(target = "id", ignore = true)
    Ubicacion toEntity(UbicacionRequestDto dto);

    @Mapping(target = "idUbicacion", source = "id")
    UbicacionResponseDto toResponse(Ubicacion entity);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(UbicacionRequestDto dto, @MappingTarget Ubicacion entity);

    List<UbicacionResponseDto> toResponseList(List<Ubicacion> ubicaciones);
}
