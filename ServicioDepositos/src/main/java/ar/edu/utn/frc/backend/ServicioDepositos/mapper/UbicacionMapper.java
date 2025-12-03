package ar.edu.utn.frc.backend.ServicioDepositos.mapper;

import ar.edu.utn.frc.backend.ServicioDepositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.model.Ubicacion;
import org.springframework.stereotype.Component;

@Component
public class UbicacionMapper implements GenericMapper<Ubicacion, UbicacionResponseDto, UbicacionRequestDto> {

    @Override
    public Ubicacion toEntity(UbicacionRequestDto dto) {
        Ubicacion ubicacion = new Ubicacion();

        ubicacion.setDireccion(dto.getDireccion());
        ubicacion.setLatitud(dto.getLatitud());
        ubicacion.setLongitud(dto.getLongitud());
        ubicacion.setNombreCiudad(dto.getNombreCiudad());

        return ubicacion;
    }

    @Override
    public UbicacionResponseDto toResponse(Ubicacion entity){
        return UbicacionResponseDto.builder()
                .idUbicacion(entity.getId())
                .direccion(entity.getDireccion())
                .latitud(entity.getLatitud())
                .longitud(entity.getLongitud())
                .nombreCiudad(entity.getNombreCiudad())
                .build();
    }
}
