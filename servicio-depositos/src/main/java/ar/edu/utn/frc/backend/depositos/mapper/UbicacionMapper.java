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
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = GeometryMapper.class
)
public interface UbicacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(
        target = "coordenadas",
        expression = "java(geometryMapper.toPoint(dto.getLatitud(), dto.getLongitud()))"
    )
    Ubicacion toEntity(UbicacionRequestDto dto);

    @Mapping(target = "idUbicacion", source = "id")
    @Mapping(target = "latitud", source = "coordenadas", qualifiedByName = "toLat")
    @Mapping(target = "longitud", source = "coordenadas", qualifiedByName = "toLon")
    UbicacionResponseDto toResponse(Ubicacion entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(
        target = "coordenadas",
        expression = "java(geometryMapper.toPoint(dto.getLatitud(), dto.getLongitud()))"
    )
    void updateFromDto(UbicacionRequestDto dto, @MappingTarget Ubicacion entity);

    List<UbicacionResponseDto> toResponseList(List<Ubicacion> ubicaciones);
}
