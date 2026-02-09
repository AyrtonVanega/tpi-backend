package ar.edu.utn.frc.backend.tarifas.workflow;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.tarifas.dto.CreateTarifaDto;
import ar.edu.utn.frc.backend.tarifas.service.interfaces.ITarifaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrearTarifaWorkflow {

    private final ITarifaService tarifaService;

    public void crearTarifa(CreateTarifaDto dto) {

        double pesoMin = dto.getRangoPesoMin();
        double pesoMax = dto.getRangoPesoMax();
        double volMin = dto.getRangoVolumenMin();
        double volMax = dto.getRangoVolumenMax();

        // Valida que no exista solapamiento entre los rangos de la nueva tarifa y las
        // tarifas existentes
        tarifaService.validarRangosTarifa(pesoMin, pesoMax, volMin, volMax);

        // Crea la Tarifa
        tarifaService.crear(dto);
    }
}
