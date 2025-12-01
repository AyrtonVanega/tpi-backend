package ar.edu.utn.frc.backend.ServicioDepositos.mapper;

import org.mapstruct.Mapper;

import ar.edu.utn.frc.backend.ServicioDepositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.model.Deposito;

@Mapper(componentModel = "spring", uses = UbicacionMapper.class)
public interface DepositoMapper extends GenericMapper<Deposito, DepositoRequestDto, DepositoResponseDto> {
    
    @Override
    Deposito toEntity(DepositoRequestDto dto);

    @Override
    DepositoResponseDto toResponse(Deposito entity);
}
