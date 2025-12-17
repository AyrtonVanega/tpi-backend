package ar.edu.utn.frc.backend.solicitudes.mapper;

import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.solicitudes.dto.EstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.EstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.model.EstadoContenedor;

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
