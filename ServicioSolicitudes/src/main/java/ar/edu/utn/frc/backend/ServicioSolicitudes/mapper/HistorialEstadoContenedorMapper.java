package ar.edu.utn.frc.backend.ServicioSolicitudes.mapper;

import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.HistorialEstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.HistorialEstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.model.HistorialEstadoContenedor;
import org.springframework.stereotype.Component;

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
