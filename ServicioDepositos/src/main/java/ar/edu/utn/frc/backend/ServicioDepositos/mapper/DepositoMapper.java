package ar.edu.utn.frc.backend.ServicioDepositos.mapper;

import ar.edu.utn.frc.backend.ServicioDepositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.model.Deposito;
import org.springframework.stereotype.Component;

@Component
public class DepositoMapper implements GenericMapper<Deposito, DepositoResponseDto, DepositoRequestDto> {
    
    public Deposito toEntity(DepositoRequestDto dto) {
        Deposito deposito = new Deposito();

        deposito.setDireccion(dto.getDireccion());
        deposito.setLatitud(dto.getLatitud());
        deposito.setLongitud(dto.getLongitud());
        deposito.setNombreCiudad(dto.getNombreCiudad());
        deposito.setCostoEstadiaDiaria(dto.getCostoEstadiaDiaria());

        return deposito;
    }

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
