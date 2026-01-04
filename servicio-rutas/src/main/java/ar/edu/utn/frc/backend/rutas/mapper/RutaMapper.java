package ar.edu.utn.frc.backend.rutas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.rutas.dto.RutaTentativaDto;
import ar.edu.utn.frc.backend.rutas.model.Ruta;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface RutaMapper {
    
    @Mapping(target = "idRuta", ignore = true)
    @Mapping(target = "tramos", ignore = true)
    Ruta toEntity(RutaTentativaDto dto);
}
