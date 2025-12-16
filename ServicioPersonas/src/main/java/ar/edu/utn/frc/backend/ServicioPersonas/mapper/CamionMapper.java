package ar.edu.utn.frc.backend.ServicioPersonas.mapper;

import ar.edu.utn.frc.backend.ServicioPersonas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.ServicioPersonas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.ServicioPersonas.model.Camion;

public class CamionMapper implements GenericMapper<Camion, CamionResponseDto, CamionRequestDto> {

    @Override
    public Camion toEntity(CamionRequestDto dto) {
        Camion camion = new Camion();

        camion.setPatente(dto.getPatente());
        camion.setVolumen(dto.getVolumen());
        camion.setPeso(dto.getPeso());
        camion.setDisponibilidad(dto.isDisponibilidad());
        camion.setCostoBaseKm(dto.getCostoBaseKm());
        camion.setConsumoCombustiblePromedio(dto.getConsumoCombustiblePromedio());

        return camion;
    }

    @Override
    public CamionResponseDto toResponse(Camion entity) {
        return CamionResponseDto.builder()
                .patente(entity.getPatente())
                .volumen(entity.getVolumen())
                .peso(entity.getPeso())
                .disponibilidad(entity.isDisponibilidad())
                .costoBaseKm(entity.getCostoBaseKm())
                .consumoCombustiblePromedio(entity.getConsumoCombustiblePromedio())
                .build();
    }   
}
