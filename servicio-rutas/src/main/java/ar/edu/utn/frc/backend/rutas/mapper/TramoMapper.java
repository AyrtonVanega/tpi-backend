package ar.edu.utn.frc.backend.rutas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

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
    Tramo toEntity(TramoTentativoDto dto);
}
