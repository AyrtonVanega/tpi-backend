package ar.edu.utn.frc.backend.solicitudes.mapper;

import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.model.Solicitud;

@Component
public class SolicitudMapper implements GenericMapper<Solicitud, SolicitudResponseDto, SolicitudRequestDto> {

    @Override
    public Solicitud toEntity(SolicitudRequestDto dto) {
        Solicitud solicitud = new Solicitud();

        solicitud.setFechaHoraInicio(dto.getFechaHoraInicio());
        solicitud.setFechaHoraFin(dto.getFechaHoraFin());
        solicitud.setCostoEstimado(dto.getCostoEstimado());
        solicitud.setTiempoEstimado(dto.getTiempoEstimado());
        solicitud.setCostoReal(dto.getCostoReal());
        solicitud.setTiempoReal(dto.getTiempoReal());

        return solicitud;
    }

    @Override
    public SolicitudResponseDto toResponse(Solicitud entity) {
        return SolicitudResponseDto.builder()
                .idSolicitud(entity.getIdSolicitud())
                .fechaHoraInicio(entity.getFechaHoraInicio())
                .fechaHoraFin(entity.getFechaHoraFin())
                .costoEstimado(entity.getCostoEstimado())
                .tiempoEstimado(entity.getTiempoEstimado())
                .costoReal(entity.getCostoReal())
                .tiempoReal(entity.getTiempoReal())
                .build();
    }
}
