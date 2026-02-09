package ar.edu.utn.frc.backend.personas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.personas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.personas.exception.BusinessException;
import ar.edu.utn.frc.backend.personas.exception.ResourceNotFoundException;
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

    @Override
    public Camion crear(String patente, double volumen, double peso, double costoBaseKm,
            double consumoCombustiblePromedio, Transportista transportista) {

        Camion camion = new Camion(patente, volumen, peso, true, costoBaseKm, consumoCombustiblePromedio,
                transportista);

        return camionRepository.save(camion);
    }

    @Override
    public Camion actualizar(String patenteCamion, CamionRequestDto dto) {
        // Busca el camion en la BD
        Camion camion = camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Camion " + patenteCamion + " no encontrado");
                });

        // Mapea datos simples
        camionMapper.updateFromPutDto(dto, camion);

        // Guarda los cambios
        return camionRepository.save(camion);
    }

    @Override
    public CamionResponseDto obtenerPorId(String patenteCamion) {
        // Busca el camion en la BD
        Camion camion = camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Camion " + patenteCamion + " no encontrado");
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
                    return new ResourceNotFoundException("Camion " + patenteCamion + " no encontrado");
                });

        if (!camion.isDisponibilidad()) {
            throw new BusinessException("El Camion " + patenteCamion + " no esta disponible");
        }

        camion.setDisponibilidad(false);
        camionRepository.save(camion);
    }

    @Override
    public CamionResponseDto finalizarRecorrido(String patenteCamion) {
        Camion camion = camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Camion " + patenteCamion + " no encontrado");
                });

        camion.setDisponibilidad(true);
        camionRepository.save(camion);

        return camionMapper.toResponse(camion);
    }

    @Override
    public Camion buscarCamionPorId(String patenteCamion) {
        return camionRepository.findById(patenteCamion)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Camion " + patenteCamion + " no encontrado");
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

    @Override
    public Camion obtenerCamionPorTransportista(String docTransportista, String tipoDocTransportista) {
        return camionRepository.buscarCamionPorTransportista(docTransportista, tipoDocTransportista)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Camion no encontrado para Transportista con doc: "
                            + docTransportista + " y tipoDoc: " + tipoDocTransportista);
                });
    }
}
