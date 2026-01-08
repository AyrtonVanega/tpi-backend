package ar.edu.utn.frc.backend.tarifas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.tarifas.dto.TarifaRequestDto;
import ar.edu.utn.frc.backend.tarifas.dto.TarifaResponseDto;

public interface ITarifaService {

    void validarRangosTarifa(double pesoMin, double pesoMax, double volMin, double volMax);

    void crear(TarifaRequestDto dto, double consumoCombustibleGralAprox);

    void actualizar(Long idTarifa, TarifaRequestDto dto);

    void eliminar(Long idTarifa);

    TarifaResponseDto obtenerPorId(Long idTarifa);

    List<TarifaResponseDto> obtenerTodos();
}
