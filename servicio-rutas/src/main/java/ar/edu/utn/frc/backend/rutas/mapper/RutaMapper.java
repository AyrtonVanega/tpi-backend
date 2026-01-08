package ar.edu.utn.frc.backend.rutas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.rutas.dto.RutaResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.RutaTentativaDto;
import ar.edu.utn.frc.backend.rutas.model.Ruta;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface RutaMapper {
    
    @Mapping(target = "idRuta", ignore = true)
    @Mapping(target = "tramos", ignore = true)
    @Mapping(target = "costoReal", ignore = true)
    @Mapping(target = "tiempoReal", ignore = true)
    @Mapping(target = "idSolicitud", ignore = true)
    @Mapping(target = "detallesCostoRuta", ignore = true)
    Ruta toEntity(RutaTentativaDto dto);

    @Mapping(target = "tramos", ignore = true)
    RutaResponseDto toResponse(Ruta entity);
}
