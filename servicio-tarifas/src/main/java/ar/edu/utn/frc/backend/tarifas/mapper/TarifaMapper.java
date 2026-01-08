package ar.edu.utn.frc.backend.tarifas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.tarifas.dto.CreateTarifaDto;
import ar.edu.utn.frc.backend.tarifas.dto.TarifaResponseDto;
import ar.edu.utn.frc.backend.tarifas.model.Tarifa;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TarifaMapper {
    
    @Mapping(target = "idTarifa", ignore = true)
    @Mapping(target = "consumoCombustibleGralAprox", ignore = true)
    Tarifa toEntity(CreateTarifaDto dto);

    TarifaResponseDto toResponse(Tarifa entity);

    List<TarifaResponseDto> toResponseList(List<Tarifa> tarifas);
}
