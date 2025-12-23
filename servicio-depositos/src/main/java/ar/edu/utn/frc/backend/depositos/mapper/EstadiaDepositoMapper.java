package ar.edu.utn.frc.backend.depositos.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.depositos.dto.CreateEstadiaDepositoDto;
import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.dto.PatchEstadiaDepositoDto;
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

    @Mapping(target = "idDeposito", source = "idEstadiaDeposito.idDeposito")
    @Mapping(target = "codigoEstado", source = "estado.codigo")
    EstadiaDepositoResponseDto toResponse(EstadiaDeposito entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idEstadiaDeposito", ignore = true)
    @Mapping(target = "deposito", ignore = true)
    @Mapping(target = "fechaHoraEntrada", ignore = true)
    @Mapping(target = "idSolicitud", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void updateFromPatchDto(PatchEstadiaDepositoDto dto, @MappingTarget EstadiaDeposito entity);

    List<EstadiaDepositoResponseDto> toResponseList(List<EstadiaDeposito> estadias);
}
