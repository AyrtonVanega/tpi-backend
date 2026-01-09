package ar.edu.utn.frc.backend.personas.workflow;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.client.RutaClient;
import ar.edu.utn.frc.backend.personas.client.dto.TramoResponseDto;
import ar.edu.utn.frc.backend.personas.model.Transportista;
import ar.edu.utn.frc.backend.personas.service.interfaces.ITransportistaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultarTramosAsignadosWorkflow {

    private final RutaClient rutaClient;
    private final ITransportistaService transportistaService;

    public List<TramoResponseDto> consultarTramosAsignados(String docTransportista, String tipoDocTransportista) {

        Transportista transportista = transportistaService.obtenerTransportistaPorId(docTransportista,
                tipoDocTransportista);

        List<TramoResponseDto> tramosAsignados = rutaClient
                .buscarTramosPorPatenteYEstado(transportista.getCamion().getPatente(), "ASIGNADO");

        return tramosAsignados;
    }
}
