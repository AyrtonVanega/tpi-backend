package ar.edu.utn.frc.backend.depositos.mapper;

import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.depositos.dto.EstadoEstadiaDepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.EstadoEstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.model.EstadoEstadiaDeposito;

@Component
public class EstadoEstadiaDepositoMapper implements GenericMapper<
        EstadoEstadiaDeposito, EstadoEstadiaDepositoResponseDto, EstadoEstadiaDepositoRequestDto> {

    @Override
    public EstadoEstadiaDeposito toEntity(EstadoEstadiaDepositoRequestDto dto) {
        return EstadoEstadiaDeposito.builder()
                .codigo(dto.getCodigo())
                .descripcion(dto.getDescripcion())
                .build();
    }

    @Override
    public EstadoEstadiaDepositoResponseDto toResponse(EstadoEstadiaDeposito estado) {
        return EstadoEstadiaDepositoResponseDto.builder()
                .idEstado(estado.getIdEstado())
                .codigo(estado.getCodigo())
                .descripcion(estado.getDescripcion())
                .build();
    }
}
