package ar.edu.utn.frc.backend.solicitudes.mapper;

import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.solicitudes.dto.HistorialEstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.HistorialEstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.model.HistorialEstadoContenedor;

@Component
public class HistorialEstadoContenedorMapper implements GenericMapper<
        HistorialEstadoContenedor, HistorialEstadoContenedorResponseDto, HistorialEstadoContenedorRequestDto> {

    @Override
    public HistorialEstadoContenedor toEntity(HistorialEstadoContenedorRequestDto dto) {
        HistorialEstadoContenedor historialEstadoContenedor = new HistorialEstadoContenedor();

        historialEstadoContenedor.setFechaHora(dto.getFechaHora());

        return historialEstadoContenedor;
    }

    @Override
    public HistorialEstadoContenedorResponseDto toResponse(HistorialEstadoContenedor entity) {
        return HistorialEstadoContenedorResponseDto.builder()
                .idHistorialEstadoContenedor(entity.getIdHistorial())
                .fechaHora(entity.getFechaHora())
                .build();
    }
}
