package ar.edu.utn.frc.backend.rutas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.rutas.dto.DetalleCostoTramoDto;
import ar.edu.utn.frc.backend.rutas.model.DetalleCostoTramo;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DetalleCostoTramoMapper {

    DetalleCostoTramoDto toResponse(DetalleCostoTramo entity);

    List<DetalleCostoTramoDto> toResponseList(List<DetalleCostoTramo> detalleCostoTramos);
}
