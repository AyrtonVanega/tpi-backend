package ar.edu.utn.frc.backend.ServicioDepositos.mapper;

import org.mapstruct.Mapper;

import ar.edu.utn.frc.backend.ServicioDepositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.model.Ubicacion;

@Mapper(componentModel = "spring")
public interface UbicacionMapper extends GenericMapper<Ubicacion, UbicacionRequestDto, UbicacionResponseDto> {
    
    @Override
    Ubicacion toEntity(UbicacionRequestDto dto);

    @Override
    UbicacionResponseDto toResponse(Ubicacion entity);
}
