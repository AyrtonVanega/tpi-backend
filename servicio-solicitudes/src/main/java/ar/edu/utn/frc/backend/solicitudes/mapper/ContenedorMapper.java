package ar.edu.utn.frc.backend.solicitudes.mapper;

import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;

@Component
public class ContenedorMapper implements GenericMapper<Contenedor, ContenedorResponseDto, ContenedorRequestDto> {

    @Override
    public Contenedor toEntity(ContenedorRequestDto dto) {
        Contenedor contenedor = new Contenedor();

        contenedor.setAncho(dto.getAncho());
        contenedor.setAlto(dto.getAlto());
        contenedor.setLargo(dto.getLargo());
        contenedor.setPeso(dto.getPeso());

        return contenedor;
    }

    @Override
    public ContenedorResponseDto toResponse(Contenedor entity) {
        return ContenedorResponseDto.builder()
                .idContenedor(entity.getIdContenedor())
                .ancho(entity.getAncho())
                .alto(entity.getAlto())
                .largo(entity.getLargo())
                .peso(entity.getPeso())
                .idSolicitud(entity.getSolicitud().getIdSolicitud())
                .build();
    }
}
