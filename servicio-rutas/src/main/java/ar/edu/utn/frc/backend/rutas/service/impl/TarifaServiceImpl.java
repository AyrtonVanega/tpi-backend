package ar.edu.utn.frc.backend.rutas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.dto.TarifaRequestDto;
import ar.edu.utn.frc.backend.rutas.dto.TarifaResponseDto;
import ar.edu.utn.frc.backend.rutas.mapper.TarifaMapper;
import ar.edu.utn.frc.backend.rutas.model.Tarifa;
import ar.edu.utn.frc.backend.rutas.repository.TarifaRepository;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITarifaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TarifaServiceImpl implements ITarifaService {

    private final TarifaRepository tarifaRepository;
    private final TarifaMapper tarifaMapper;

    @Override
    public void crear(TarifaRequestDto dto) {
        Tarifa tarifa = tarifaMapper.toEntity(dto);
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
