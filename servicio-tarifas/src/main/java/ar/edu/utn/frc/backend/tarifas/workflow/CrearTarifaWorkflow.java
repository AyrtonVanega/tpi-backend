package ar.edu.utn.frc.backend.tarifas.workflow;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.tarifas.client.PersonaClient;
import ar.edu.utn.frc.backend.tarifas.dto.CreateTarifaDto;
import ar.edu.utn.frc.backend.tarifas.service.interfaces.ITarifaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrearTarifaWorkflow {

    private final ITarifaService tarifaService;
    private final PersonaClient personaClient;

    public void crearTarifa(CreateTarifaDto dto) {

        double pesoMin = dto.getRangoPesoMin();
        double pesoMax = dto.getRangoPesoMax();
        double volMin = dto.getRangoVolumenMin();
        double volMax = dto.getRangoVolumenMax();

        tarifaService.validarRangosTarifa(pesoMin, pesoMax, volMin, volMax);

        double consumoCombustibleGralAprox = personaClient.calcularConsumoPromedio(pesoMin, pesoMax, volMin, volMax);

        tarifaService.crear(dto, consumoCombustibleGralAprox);
    }
}
