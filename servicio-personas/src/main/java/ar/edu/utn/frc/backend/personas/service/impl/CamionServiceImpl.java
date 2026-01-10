package ar.edu.utn.frc.backend.personas.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.personas.dto.CamionResponseDto;
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
            return c;
        });
    }

    @Override
    public void actualizar(String patenteCamion, CamionRequestDto dto) {
        // Busca el camion en la BD
        Camion camion = camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", patenteCamion);
                    return new RuntimeException();
                });

        // Mapea datos simples
        camionMapper.updateFromPutDto(dto, camion);

        // Guarda los cambios
        camionRepository.save(camion);
    }

    @Override
    public CamionResponseDto obtenerPorId(String patenteCamion) {
        // Busca el camion en la BD
        Camion camion = camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", patenteCamion);
                    return new RuntimeException();
                });

        // Mapea datos simples Entity -> DTO
        CamionResponseDto responseDto = camionMapper.toResponse(camion);

        // Setea doc y tipoDoc del Transportista al ResponseDto
        String docTransportista = camion.getTransportista().getIdPersona().getDoc();
        String tipoDocTransportista = camion.getTransportista().getIdPersona().getTipoDoc();
        responseDto.setDocTransportista(docTransportista);
        responseDto.setTipoDocTransportista(tipoDocTransportista);

        return responseDto;
    }

    @Override
    public List<CamionResponseDto> obtenerCamionesDisponibles() {
        // Obtiene los camiones disponibles de la BD
        List<Camion> camiones = camionRepository.findByDisponibilidadTrue();

        // Mapea datos simples Entity -> DTO
        List<CamionResponseDto> responseDtoList = camionMapper.toResponseList(camiones);

        // Setea doc y tipoDoc del Transportista a cada ResponseDto de la lista
        for (int i = 0; i < camiones.size(); i++) {
            Camion camion = camiones.get(i);
            CamionResponseDto dto = responseDtoList.get(i);

            String docTransportista = camion.getTransportista().getIdPersona().getDoc();
            String tipoDocTransportista = camion.getTransportista().getIdPersona().getTipoDoc();
            dto.setDocTransportista(docTransportista);
            dto.setTipoDocTransportista(tipoDocTransportista);
        }

        return responseDtoList;
    }

    @Override
    public void reservarCamion(String patenteCamion) {
        Camion camion = camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", patenteCamion);
                    return new RuntimeException();
                });

        if (!camion.isDisponibilidad()) {
            log.error("El Camion {} no esta disponible", patenteCamion);
            throw new RuntimeException();
        }

        camion.setDisponibilidad(false);
        camionRepository.save(camion);
    }

    @Override
    public CamionResponseDto finalizarRecorrido(String patenteCamion) {
        Camion camion = camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", patenteCamion);
                    return new RuntimeException();
                });

        camion.setDisponibilidad(true);
        camionRepository.save(camion);

        return camionMapper.toResponse(camion);
    }

    @Override
    public Camion buscarCamionPorId(String patenteCamion) {
        return camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    log.error("Camion {} no encontrado", patenteCamion);
                    return new RuntimeException();
                });
    }

    @Override
    public double calcularConsumoPromedio(double pesoMin, double pesoMax, double volMin, double volMax) {
        List<Camion> camiones = camionRepository.buscarCamionesPorCapacidad(pesoMin, pesoMax, volMin, volMax);
        return camiones.stream()
                .mapToDouble(Camion::getConsumoCombustiblePromedio)
                .average()
                .orElse(0.0);
    }
}
