package ar.edu.utn.frc.backend.tarifas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.tarifas.dto.TarifaRequestDto;
import ar.edu.utn.frc.backend.tarifas.dto.TarifaResponseDto;
import ar.edu.utn.frc.backend.tarifas.mapper.TarifaMapper;
import ar.edu.utn.frc.backend.tarifas.model.Tarifa;
import ar.edu.utn.frc.backend.tarifas.repository.TarifaRepository;
import ar.edu.utn.frc.backend.tarifas.service.interfaces.ITarifaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TarifaServiceImpl implements ITarifaService {

    private final TarifaRepository tarifaRepository;
    private final TarifaMapper tarifaMapper;

    @Override
    public void validarRangosTarifa(double pesoMin, double pesoMax, double volMin, double volMax) {
        boolean existeSolapamiento = tarifaRepository.existeSolapamiento(pesoMin, pesoMax, volMin, volMax);
        if (existeSolapamiento) {
            log.error("No se puede crear la Tarifa porque existe un solapamiento entre los rangos");
            throw new RuntimeException();
        }
    }

    @Override
    public void crear(TarifaRequestDto dto, double consumoCombustibleGralAprox) {
        Tarifa tarifa = tarifaMapper.toEntity(dto);
        tarifa.setConsumoCombustibleGralAprox(consumoCombustibleGralAprox);
        tarifaRepository.save(tarifa);
    }

    @Override
    public void actualizar(Long idTarifa, TarifaRequestDto dto) {
        Tarifa tarifa = tarifaRepository.findById(idTarifa)
                .orElseThrow(() -> {
                    log.error("Tarifa {} no encontrada", idTarifa);
                    return new RuntimeException();
                });
        tarifaMapper.updateFromDto(dto, tarifa);
        tarifaRepository.save(tarifa);
    }

    @Override
    public void eliminar(Long idTarifa) {
        Tarifa tarifa = tarifaRepository.findById(idTarifa)
                .orElseThrow(() -> {
                    log.error("Tarifa {} no encontrada", idTarifa);
                    return new RuntimeException();
                });
        tarifaRepository.delete(tarifa);
    }

    @Override
    public TarifaResponseDto obtenerPorId(Long idTarifa) {
        Tarifa tarifa = tarifaRepository.findById(idTarifa)
                .orElseThrow(() -> {
                    log.error("Tarifa {} no encontrada", idTarifa);
                    return new RuntimeException();
                });
        return tarifaMapper.toResponse(tarifa);
    }

    @Override
    public List<TarifaResponseDto> obtenerTodos() {
        List<Tarifa> tarifas = tarifaRepository.findAll();
        return tarifaMapper.toResponseList(tarifas);
    }
}
