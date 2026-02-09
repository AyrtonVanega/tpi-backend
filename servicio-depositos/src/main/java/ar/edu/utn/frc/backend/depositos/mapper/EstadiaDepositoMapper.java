package ar.edu.utn.frc.backend.depositos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.depositos.dto.CreateEstadiaDepositoDto;
import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.model.EstadiaDeposito;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface EstadiaDepositoMapper {

    @Mapping(target = "idEstadiaDeposito", ignore = true)
    @Mapping(target = "deposito", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "fechaHoraSalida", ignore = true)
    EstadiaDeposito toEntity(CreateEstadiaDepositoDto dto);

    @Mapping(target = "idDeposito", ignore = true)
    @Mapping(target = "codigoEstado", ignore = true)
    @Mapping(target = "descripcionEstado", ignore = true)
    EstadiaDepositoResponseDto toResponse(EstadiaDeposito entity);

    List<EstadiaDepositoResponseDto> toResponseList(List<EstadiaDeposito> estadias);
}
