package ar.edu.utn.frc.backend.personas.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.personas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.personas.dto.PatchCamionDto;
import ar.edu.utn.frc.backend.personas.mapper.CamionMapper;
import ar.edu.utn.frc.backend.personas.model.Camion;
import ar.edu.utn.frc.backend.personas.model.Transportista;
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

    private final Map<String, Camion> camiones = new HashMap<>();

    @Override
    public Camion crearSiNoExiste(String patente, double volumen, double peso, double costoBaseKm,
            double consumoCombustiblePromedio, Transportista transportista) {

        return this.camiones.computeIfAbsent(patente, pat -> {
            Camion c = new Camion(
                    pat,
                    volumen,
                    peso,
                    true,
                    costoBaseKm,
                    consumoCombustiblePromedio,
                    transportista);
            return camionRepository.save(c);
        });
    }

    @Override
    public void actualizar(String patenteCamion, CamionRequestDto dto) {
        Camion camion = camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", patenteCamion);
                    return new RuntimeException();
                });

        camionMapper.updateFromPutDto(dto, camion);
        camionRepository.save(camion);
    }

    @Override
    public void eliminar(String patenteCamion) {
        Camion camion = camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", patenteCamion);
                    return new RuntimeException();
                });

        camionRepository.delete(camion);
    }

    @Override
    public CamionResponseDto obtenerPorId(String patenteCamion) {
        Camion camion = camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", patenteCamion);
                    return new RuntimeException();
                });
            
        return camionMapper.toResponse(camion);
    }

    @Override
    public List<CamionResponseDto> obtenerCamionesDisponibles() {
        List<Camion> camiones = camionRepository.findByDisponibilidadTrue();
        return camionMapper.toResponseList(camiones);
    }

    @Override
    public void actualizarDisponibilidad(String patenteCamion, PatchCamionDto patchCamionDto) {
        Camion camion = camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", patenteCamion);
                    return new RuntimeException();
                });

        camion.setDisponibilidad(patchCamionDto.isDisponibilidad());
        camionRepository.save(camion);
    }
}
