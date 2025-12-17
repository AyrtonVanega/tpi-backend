package ar.edu.utn.frc.backend.personas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.personas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.personas.mapper.CamionMapper;
import ar.edu.utn.frc.backend.personas.model.Camion;
import ar.edu.utn.frc.backend.personas.repository.CamionRepository;
import ar.edu.utn.frc.backend.personas.service.interfaces.ICamionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CamionServiceImpl implements ICamionService {

    private final CamionRepository camionRepository;
    private final CamionMapper camionMapper;

    @Override
    public CamionResponseDto crear(CamionRequestDto dto) {
        Camion camion = camionMapper.toEntity(dto);
        camionRepository.save(camion);
        return camionMapper.toResponse(camion);
    }

    @Override
    public CamionResponseDto actualizar(String idCamion, CamionRequestDto dto) {
        Camion camion = camionRepository.findById(idCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", idCamion);
                    return new RuntimeException();
                });

        camion.setVolumen(dto.getVolumen());
        camion.setPeso(dto.getPeso());
        camion.setDisponibilidad(dto.isDisponibilidad());
        camion.setCostoBaseKm(dto.getCostoBaseKm());
        camion.setConsumoCombustiblePromedio(dto.getConsumoCombustiblePromedio());

        camionRepository.save(camion);

        return camionMapper.toResponse(camion);
    }

    @Override
    public void eliminar(String idCamion) {
        Camion camion = camionRepository.findById(idCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", idCamion);
                    return new RuntimeException();
                });

        camionRepository.delete(camion);
    }

    @Override
    public CamionResponseDto obtenerPorId(String idCamion) {
        Camion camion = camionRepository.findById(idCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", idCamion);
                    return new RuntimeException();
                });
            
        return camionMapper.toResponse(camion);
    }

    @Override
    public List<CamionResponseDto> obtenerTodos() {
        return camionRepository.findAll()
                .stream()
                .map(camionMapper::toResponse)
                .toList();
    }
}
