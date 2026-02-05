package ar.edu.utn.frc.backend.depositos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.depositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.model.Deposito;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {UbicacionMapper.class}
)
public interface DepositoMapper{

    @Mapping(target = "id", ignore = true)
    Deposito toEntity(DepositoRequestDto dto);

    @Mapping(target = "idDeposito", source = "id")
    DepositoResponseDto toResponse(Deposito entity);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(DepositoRequestDto dto, @MappingTarget Deposito entity);

    List<DepositoResponseDto> toResponseList(List<Deposito> depositos);
}
