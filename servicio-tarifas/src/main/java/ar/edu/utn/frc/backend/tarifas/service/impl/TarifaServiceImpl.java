package ar.edu.utn.frc.backend.tarifas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.tarifas.dto.CreateTarifaDto;
import ar.edu.utn.frc.backend.tarifas.dto.PatchTarifaDto;
import ar.edu.utn.frc.backend.tarifas.dto.TarifaResponseDto;
import ar.edu.utn.frc.backend.tarifas.event.CamionCreadoEvent;
import ar.edu.utn.frc.backend.tarifas.exception.BusinessException;
import ar.edu.utn.frc.backend.tarifas.exception.ResourceNotFoundException;
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
    private final CamionViewService camionViewService;

    @Override
    public void validarRangosTarifa(double pesoMin, double pesoMax, double volMin, double volMax) {
        boolean existeSolapamiento = tarifaRepository.existeSolapamiento(pesoMin, pesoMax, volMin, volMax);
        if (existeSolapamiento) {
            throw new BusinessException("No se puede crear la Tarifa porque existe un solapamiento entre los rangos");
        }
    }

    @Override
    public void crear(CreateTarifaDto dto, double consumoCombustibleGralAprox) {
        Tarifa tarifa = tarifaMapper.toEntity(dto);
        tarifa.setConsumoCombustibleGralAprox(consumoCombustibleGralAprox);
        tarifaRepository.save(tarifa);
    }

    @Override
    public void actualizarParcial(Long idTarifa, PatchTarifaDto dto) {
        Tarifa tarifa = tarifaRepository.findById(idTarifa)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Tarifa " + idTarifa + " no encontrada");
                });

        if (dto.getConsumoCombustibleGralAprox() != null) {
            tarifa.setConsumoCombustibleGralAprox(dto.getConsumoCombustibleGralAprox());
        }

        if (dto.getCostoBaseKmVolumen() != null) {
            tarifa.setCostoBaseKmVolumen(dto.getCostoBaseKmVolumen());
        }

        tarifaRepository.save(tarifa);
    }

    @Override
    public void eliminar(Long idTarifa) {
        Tarifa tarifa = tarifaRepository.findById(idTarifa)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Tarifa " + idTarifa + " no encontrada");
                });
        tarifaRepository.delete(tarifa);
    }

    @Override
    public TarifaResponseDto obtenerPorId(Long idTarifa) {
        Tarifa tarifa = tarifaRepository.findById(idTarifa)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Tarifa " + idTarifa + " no encontrada");
                });
        return tarifaMapper.toResponse(tarifa);
    }

    @Override
    public List<TarifaResponseDto> obtenerTodos() {
        List<Tarifa> tarifas = tarifaRepository.findAll();
        return tarifaMapper.toResponseList(tarifas);
    }

    @Override
    public void recalcularConsumoPromedioParaTarifasAfectadas(CamionCreadoEvent event) {

        List<Tarifa> tarifasAfectadas = tarifaRepository.buscarTarifasPorRango(
                event.getPeso(),
                event.getVolumen());

        for (Tarifa tarifa : tarifasAfectadas) {

            double promedio = camionViewService.calcularConsumoPromedio(
                    tarifa.getRangoPesoMin(),
                    tarifa.getRangoPesoMax(),
                    tarifa.getRangoVolumenMin(),
                    tarifa.getRangoVolumenMax());

            tarifa.setConsumoCombustibleGralAprox(promedio);
            tarifaRepository.save(tarifa);
        }
    }
}
