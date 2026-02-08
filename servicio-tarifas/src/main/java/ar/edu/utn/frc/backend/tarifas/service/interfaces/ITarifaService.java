package ar.edu.utn.frc.backend.tarifas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.tarifas.dto.CreateTarifaDto;
import ar.edu.utn.frc.backend.tarifas.dto.PatchTarifaDto;
import ar.edu.utn.frc.backend.tarifas.dto.TarifaResponseDto;
import ar.edu.utn.frc.backend.tarifas.event.CamionCapacidadEvent;

public interface ITarifaService {

    void validarRangosTarifa(double pesoMin, double pesoMax, double volMin, double volMax);

    void crear(CreateTarifaDto dto, double consumoCombustibleGralAprox);

    void actualizarParcial(Long idTarifa, PatchTarifaDto dto);

    void eliminar(Long idTarifa);

    TarifaResponseDto obtenerPorId(Long idTarifa);

    List<TarifaResponseDto> obtenerTodos();

    void recalcularConsumoPromedioParaTarifasAfectadas(CamionCapacidadEvent event);
}
