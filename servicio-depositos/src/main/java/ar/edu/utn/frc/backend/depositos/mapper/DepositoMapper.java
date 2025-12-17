package ar.edu.utn.frc.backend.depositos.mapper;

import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.depositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.model.Deposito;

@Component
public class DepositoMapper implements GenericMapper<Deposito, DepositoResponseDto, DepositoRequestDto> {

    @Override
    public Deposito toEntity(DepositoRequestDto dto) {
        Deposito deposito = new Deposito();

        deposito.setDireccion(dto.getDireccion());
        deposito.setLatitud(dto.getLatitud());
        deposito.setLongitud(dto.getLongitud());
        deposito.setNombreCiudad(dto.getNombreCiudad());
        deposito.setCostoEstadiaDiaria(dto.getCostoEstadiaDiaria());

        return deposito;
    }

    @Override
    public DepositoResponseDto toResponse(Deposito entity) {
        return DepositoResponseDto.builder()
                .idDeposito(entity.getId())
                .direccion(entity.getDireccion())
                .latitud(entity.getLatitud())
                .longitud(entity.getLongitud())
                .nombreCiudad(entity.getNombreCiudad())
                .costoEstadiaDiaria(entity.getCostoEstadiaDiaria())
                .build();
    }
}
