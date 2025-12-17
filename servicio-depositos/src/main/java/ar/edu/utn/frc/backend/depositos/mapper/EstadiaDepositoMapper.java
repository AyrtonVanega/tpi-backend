package ar.edu.utn.frc.backend.depositos.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.model.EstadiaDeposito;
import ar.edu.utn.frc.backend.depositos.model.EstadiaDepositoId;

@Component
@RequiredArgsConstructor
public class EstadiaDepositoMapper implements GenericMapper<
        EstadiaDeposito, EstadiaDepositoResponseDto, EstadiaDepositoRequestDto> {

    private final EstadoEstadiaDepositoMapper estadoMapper;

    @Override
    public EstadiaDeposito toEntity(EstadiaDepositoRequestDto dto) {
        EstadiaDepositoId id = new EstadiaDepositoId(
                dto.getIdDeposito(),
                dto.getIdSolicitud()
        );

        EstadiaDeposito estadia = new EstadiaDeposito();
        estadia.setIdEstadiaDeposito(id);
        estadia.setIdSolicitud(dto.getIdSolicitud());
        estadia.setFechaHoraEntrada(dto.getFechaHoraEntrada());
        estadia.setFechaHoraSalida(dto.getFechaHoraSalida());

        return estadia;
    }

    @Override
    public EstadiaDepositoResponseDto toResponse(EstadiaDeposito entity) {
        return EstadiaDepositoResponseDto.builder()
                .idDeposito(entity.getIdEstadiaDeposito().getIdDeposito())
                .idSolicitud(entity.getIdEstadiaDeposito().getIdSolicitud())
                .fechaHoraEntrada(entity.getFechaHoraEntrada())
                .fechaHoraSalida(entity.getFechaHoraSalida())
                .estado(estadoMapper.toResponse(entity.getEstado()))
                .build();
    }
}
