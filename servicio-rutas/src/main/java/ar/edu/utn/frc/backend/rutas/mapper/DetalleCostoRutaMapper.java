package ar.edu.utn.frc.backend.rutas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.rutas.dto.DetalleCostoRutaDto;
import ar.edu.utn.frc.backend.rutas.model.DetalleCostoRuta;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DetalleCostoRutaMapper {

    DetalleCostoRutaDto toResponse(DetalleCostoRuta entity);

    List<DetalleCostoRutaDto> toResponseList(List<DetalleCostoRuta> detallesCostoRuta);
}
