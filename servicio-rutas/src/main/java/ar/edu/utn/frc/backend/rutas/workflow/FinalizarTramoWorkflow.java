package ar.edu.utn.frc.backend.rutas.workflow;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.client.DepositoClient;
import ar.edu.utn.frc.backend.rutas.client.PersonaClient;
import ar.edu.utn.frc.backend.rutas.client.SolicitudClient;
import ar.edu.utn.frc.backend.rutas.client.TarifaClient;
import ar.edu.utn.frc.backend.rutas.client.dto.CamionDto;
import ar.edu.utn.frc.backend.rutas.model.Tramo;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITramoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinalizarTramoWorkflow {
    
    private final ITramoService tramoService;
    private final DepositoClient depositoClient;
    private final SolicitudClient solicitudClient;
    private final PersonaClient personaClient;
    private final TarifaClient tarifaClient;

    public void finalizarTramo(Long idRuta, int orden) {
        Tramo tramo = tramoService.obtenerTramoPorId(idRuta, orden);

        tramoService.validarFinalizacionTramo(tramo);

        LocalDateTime fechaHora = LocalDateTime.now();
        CamionDto camion = personaClient.obtenerCamionPorId(tramo.getPatenteCamion());
        double valorLitroCombustible = tarifaClient.obtenerParametrosGlobales().getValorLitroCombustible();
        tramoService.finalizarTramo(tramo, fechaHora, valorLitroCombustible, camion);

        Long idSolicitud = tramo.getRuta().getIdSolicitud();
        if (orden != tramo.getRuta().getTramos().size()) {
            // En caso de no ser el ultimo Tramo, crea la estadia y cambia el estado del
            // contenedor
            depositoClient.crearEstadia(tramo.getIdUbicacionDestino(), idSolicitud, fechaHora);
            solicitudClient.actualizarEstadoContenedor(idSolicitud, "EN_DEPOSITO");
        } else {
            // En caso de ser el ultimo Tramo
            // Calcula costo y tiempo real
            double costoRealTotal = 0.0;
            double tiempoReal = 0.0;

            // Cambia el estado del contenedor y finaliza la solicitud
            solicitudClient.actualizarEstadoContenedor(idSolicitud, "ENTREGADO");
            solicitudClient.finalizarSolicitud(idSolicitud, fechaHora, costoRealTotal, tiempoReal);
        }

        // Setea true la disponibilidad del camion
        personaClient.actualizarDisponibilidadCamion(tramo.getPatenteCamion(), true);
    }
}
