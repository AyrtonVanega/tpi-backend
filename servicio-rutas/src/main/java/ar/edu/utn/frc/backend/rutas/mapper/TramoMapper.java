package ar.edu.utn.frc.backend.rutas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.rutas.dto.TramoResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoTentativoDto;
import ar.edu.utn.frc.backend.rutas.model.Tramo;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TramoMapper {
    
    @Mapping(target = "idTramo", ignore = true)
    @Mapping(target = "ruta", ignore = true)
    @Mapping(target = "tipoTramo", ignore = true)
    @Mapping(target = "fechaHoraInicio", ignore = true)
    @Mapping(target = "fechaHoraFin", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "patenteCamion", ignore = true)
    @Mapping(target = "costoReal", ignore = true)
    @Mapping(target = "detallesCostoTramo", ignore = true)
    Tramo toEntity(TramoTentativoDto dto);

    @Mapping(target = "orden", ignore = true)
    @Mapping(target = "tipo", ignore = true)
    @Mapping(target = "codigoEstado", ignore = true)
    @Mapping(target = "descripcionEstado", ignore = true)
    TramoResponseDto toResponse(Tramo entity);

    List<TramoResponseDto> toResponseList(List<Tramo> tramos);
}
