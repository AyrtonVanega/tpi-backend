package ar.edu.utn.frc.backend.rutas.workflow;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.client.DepositoClient;
import ar.edu.utn.frc.backend.rutas.client.PersonaClient;
import ar.edu.utn.frc.backend.rutas.client.SolicitudClient;
import ar.edu.utn.frc.backend.rutas.model.Tramo;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITramoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IniciarTramoWorkflow {

    private final ITramoService tramoService;
    private final DepositoClient depositoClient;
    private final SolicitudClient solicitudClient;
    private final PersonaClient personaClient;

    public void iniciarTramo(Long idRuta, int orden) {
        Tramo tramo = tramoService.obtenerTramoPorId(idRuta, orden);

        personaClient.reservarCamion(tramo.getPatenteCamion());
        
        tramoService.validarInicioTramo(tramo);
        tramoService.iniciarTramo(tramo);

        Long idSolicitud = tramo.getRuta().getIdSolicitud();
        if (tramo.getIdTramo().getOrden() > 1) {
            // En caso de no ser el primer Tramo, finaliza la estadia del deposito en el que
            // estuvo
            depositoClient.finalizarEstadia(tramo.getIdUbicacionOrigen(), idSolicitud, LocalDateTime.now());
        } else {
            // En caso de ser el primer Tramo, cambia el estado de la solicitud
            solicitudClient.actualizarEstadoSolicitud(idSolicitud, "EN_TRANSITO");
        }

        // Actualiza el estado del contenedor
        // El id de la solicitud es el mismo q el id del contenedor
        solicitudClient.actualizarEstadoContenedor(idSolicitud, "EN_TRANSITO");
    }
}
