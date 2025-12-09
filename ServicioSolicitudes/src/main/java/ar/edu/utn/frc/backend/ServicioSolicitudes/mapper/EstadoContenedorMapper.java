package ar.edu.utn.frc.backend.ServicioSolicitudes.mapper;

import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.EstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.EstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.model.EstadoContenedor;
import org.springframework.stereotype.Component;

@Component
public class EstadoContenedorMapper implements GenericMapper<
        EstadoContenedor, EstadoContenedorResponseDto, EstadoContenedorRequestDto> {

    @Override
    public EstadoContenedor toEntity(EstadoContenedorRequestDto dto) {
        EstadoContenedor estadoContenedor = new EstadoContenedor();

        estadoContenedor.setCodigo(dto.getCodigo());
        estadoContenedor.setDescripcion(dto.getDescripcion());

        return estadoContenedor;
    }

    @Override
    public EstadoContenedorResponseDto toResponse(EstadoContenedor entity) {
        return EstadoContenedorResponseDto.builder()
                .idEstadoContenedor(entity.getIdEstadoContenedor())
                .codigo(entity.getCodigo())
                .descripcion(entity.getDescripcion())
                .build();
    }
}
