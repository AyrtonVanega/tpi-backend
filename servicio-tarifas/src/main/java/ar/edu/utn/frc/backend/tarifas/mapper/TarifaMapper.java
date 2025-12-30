package ar.edu.utn.frc.backend.tarifas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.tarifas.dto.TarifaRequestDto;
import ar.edu.utn.frc.backend.tarifas.dto.TarifaResponseDto;
import ar.edu.utn.frc.backend.tarifas.model.Tarifa;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TarifaMapper {
    
    @Mapping(target = "idTarifa", ignore = true)
    @Mapping(target = "consumoCombustibleGralAprox", ignore = true)
    Tarifa toEntity(TarifaRequestDto dto);

    TarifaResponseDto toResponse(Tarifa entity);

    @Mapping(target = "idTarifa", ignore = true)
    @Mapping(target = "consumoCombustibleGralAprox", ignore = true)
    void updateFromDto(TarifaRequestDto dto, @MappingTarget Tarifa entity);

    List<TarifaResponseDto> toResponseList(List<Tarifa> tarifas);
}
