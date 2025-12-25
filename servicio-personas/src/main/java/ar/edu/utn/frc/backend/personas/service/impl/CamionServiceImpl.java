package ar.edu.utn.frc.backend.personas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.personas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.personas.dto.PatchCamionDto;
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
    public void crear(CamionRequestDto dto) {

        // Mapea datos simples de DTO a Entidad
        Camion camion = camionMapper.toEntity(dto);

        // Setea la disponibilidad por defecto en true
        camion.setDisponibilidad(true);

        camionRepository.save(camion);
    }

    @Override
    public void actualizar(String idCamion, CamionRequestDto dto) {
        Camion camion = camionRepository.findById(idCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", idCamion);
                    return new RuntimeException();
                });

        camionMapper.updateFromPutDto(dto, camion);
        camionRepository.save(camion);
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
        List<Camion> camiones = camionRepository.findAll();
        return camionMapper.toResponseList(camiones);
    }

    @Override
    public void actualizarDisponibilidad(String patente, PatchCamionDto patchCamionDto) {
        Camion camion = camionRepository.findById(patente)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", patente);
                    return new RuntimeException();
                });

        camion.setDisponibilidad(patchCamionDto.isDisponibilidad());
        camionRepository.save(camion);
    }
}
