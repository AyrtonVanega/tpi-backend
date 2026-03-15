package ar.edu.utn.frc.backend.solicitudes.workflow;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.solicitudes.dto.ClienteDto;
import ar.edu.utn.frc.backend.solicitudes.dto.CreateSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.UbicacionDto;
import ar.edu.utn.frc.backend.solicitudes.event.SolicitudCreadaEvent;
import ar.edu.utn.frc.backend.solicitudes.infrastructure.messaging.SolicitudEventPublisher;
import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IContenedorService;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.ISolicitudService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrearSolicitudWorkflow {

        private final IContenedorService contenedorService;
        private final ISolicitudService solicitudService;
        private final SolicitudEventPublisher solicitudEventPublisher;

        public void crearSolicitud(CreateSolicitudDto solicitudRequestDto) {

                // Crea el contenedor
                Contenedor contenedor = contenedorService.crear(solicitudRequestDto.getContenedor());

                UbicacionDto origen = solicitudRequestDto.getOrigen();
                UbicacionDto destino = solicitudRequestDto.getDestino();
                ClienteDto cliente = solicitudRequestDto.getCliente();

                // Crea la solicitud
                solicitudService.crear(
                                origen.getDireccion(),
                                origen.getNombreCiudad(),
                                destino.getDireccion(),
                                destino.getNombreCiudad(),
                                contenedor,
                                cliente.getDoc(),
                                cliente.getTipoDoc());

                // Publica el evento solicitud-creada
                SolicitudCreadaEvent event = new SolicitudCreadaEvent(origen, destino, cliente);
                solicitudEventPublisher.publicarSolicitudCreada(event);
        }
}
