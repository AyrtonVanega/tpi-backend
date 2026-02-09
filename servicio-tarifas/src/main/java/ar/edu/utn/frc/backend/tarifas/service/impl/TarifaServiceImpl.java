package ar.edu.utn.frc.backend.tarifas.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.tarifas.dto.CreateTarifaDto;
import ar.edu.utn.frc.backend.tarifas.dto.PatchTarifaDto;
import ar.edu.utn.frc.backend.tarifas.dto.TarifaResponseDto;
import ar.edu.utn.frc.backend.tarifas.event.CamionCapacidadEvent;
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
            log.warn("Intento de crear tarifa con rangos que solapan con una tarifa existente: " +
                    "Peso[{}-{}], Volumen[{}-{}]", pesoMin, pesoMax, volMin, volMax);
            throw new BusinessException("No se puede crear la Tarifa porque existe un solapamiento entre los rangos");
        }
    }

    @Override
    public void crear(CreateTarifaDto dto) {
        // Mapea datos simples DTO -> Entity
        Tarifa tarifa = tarifaMapper.toEntity(dto);

        // Calcula y setea el consumoCombustibleGralAprox para la nueva tarifa
        Double consumoCombustibleGralAprox = calcularConsumoCombustibleGralAprox(
                tarifa.getRangoPesoMax(),
                tarifa.getRangoVolumenMax());
        tarifa.setConsumoCombustibleGralAprox(consumoCombustibleGralAprox);

        // Guarda en la BD
        tarifaRepository.save(tarifa);

        log.info("Tarifa creada con ID {}, ConsumoAprox={}", tarifa.getIdTarifa(),
                tarifa.getConsumoCombustibleGralAprox());
    }

    @Override
    public void actualizarParcial(Long idTarifa, PatchTarifaDto dto) {
        Tarifa tarifa = tarifaRepository.findById(idTarifa)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Tarifa " + idTarifa + " no encontrada");
                });

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
    public void recalcularConsumoPromedioParaTarifasAfectadas(CamionCapacidadEvent event) {
        // Obtiene todas las tarifas existentes
        List<Tarifa> tarifas = tarifaRepository.findAll();

        // Para cada tarifa, actualiza su consumoCombustibleGralAprox si el camion del
        // evento afecta su rango
        for (Tarifa tarifa : tarifas) {

            Double nuevoPromedio = camionViewService.calcularConsumoPromedio(
                    tarifa.getRangoPesoMax(),
                    tarifa.getRangoVolumenMax());

            // Solo guarda si hubo un cambio real
            if (!Objects.equals(tarifa.getConsumoCombustibleGralAprox(), nuevoPromedio)) {
                tarifa.setConsumoCombustibleGralAprox(nuevoPromedio);
                tarifaRepository.save(tarifa);
                log.info("ConsumoCombustibleGralAprox recalculado para la Tarifa {}: nuevo valor={}",
                        tarifa.getIdTarifa(), nuevoPromedio);
            }
        }
    }

    @Override
    public Double calcularConsumoCombustibleGralAprox(double rangoPesoMax, double rangoVolumenMax) {

        return camionViewService.calcularConsumoPromedio(
                rangoPesoMax,
                rangoVolumenMax);
    }
}
