package ar.edu.utn.frc.backend.tarifas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.tarifas.event.CamionActualizadoEvent;
import ar.edu.utn.frc.backend.tarifas.event.CamionCreadoEvent;
import ar.edu.utn.frc.backend.tarifas.model.CamionView;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CamionViewMapper {
    
    CamionView toEntity(CamionCreadoEvent event);

    CamionView updateFromEvent(CamionActualizadoEvent event, @MappingTarget CamionView camionView);
}
