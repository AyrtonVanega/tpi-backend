package ar.edu.utn.frc.backend.rutas.workflow;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.client.DepositoClient;
import ar.edu.utn.frc.backend.rutas.client.PersonaClient;
import ar.edu.utn.frc.backend.rutas.client.SolicitudClient;
import ar.edu.utn.frc.backend.rutas.client.TarifaClient;
import ar.edu.utn.frc.backend.rutas.client.dto.CamionDto;
import ar.edu.utn.frc.backend.rutas.client.dto.ParametroGlobalDto;
import ar.edu.utn.frc.backend.rutas.model.Ruta;
import ar.edu.utn.frc.backend.rutas.model.Tramo;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IRutaService;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITramoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinalizarTramoWorkflow {

    private final ITramoService tramoService;
    private final IRutaService rutaService;
    private final DepositoClient depositoClient;
    private final SolicitudClient solicitudClient;
    private final PersonaClient personaClient;
    private final TarifaClient tarifaClient;

    public void finalizarTramo(Long idRuta, int orden) {
        Tramo tramo = tramoService.obtenerTramoPorId(idRuta, orden);

        tramoService.validarFinalizacionTramo(tramo);

        LocalDateTime fechaHora = LocalDateTime.now();
        CamionDto camion = personaClient.finalizarRecorridoCamion(tramo.getPatenteCamion());
        ParametroGlobalDto parametroGlobal = tarifaClient.obtenerParametrosGlobales();

        // Finaliza el tramo
        tramoService.finalizarTramo(tramo, fechaHora, camion, parametroGlobal.getValorLitroCombustible());

        Ruta ruta = tramo.getRuta();
        Long idSolicitud = ruta.getIdSolicitud();
        if (orden != ruta.getTramos().size()) {
            // En caso de no ser el ultimo Tramo, crea la estadia y cambia el estado del
            // contenedor
            depositoClient.crearEstadia(tramo.getIdUbicacionDestino(), idSolicitud, fechaHora);
            solicitudClient.actualizarEstadoContenedor(idSolicitud, "EN_DEPOSITO");
        } else {
            // En caso de ser el ultimo Tramo
            // Calcula costo y tiempo real
            double costoTotalEstadias = depositoClient.calcularCostoEstadiaDiario(idSolicitud);

            // Finaliza la ruta
            rutaService.finalizarRuta(ruta, parametroGlobal.getCostoGestionBase(), costoTotalEstadias);

            // Finaliza la solicitud
            solicitudClient.finalizarSolicitud(idSolicitud, fechaHora, ruta.getTiempoReal(), ruta.getCostoReal());
        }
    }
}
