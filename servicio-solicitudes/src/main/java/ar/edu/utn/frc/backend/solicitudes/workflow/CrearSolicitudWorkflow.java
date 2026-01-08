package ar.edu.utn.frc.backend.solicitudes.workflow;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.solicitudes.client.DepositoClient;
import ar.edu.utn.frc.backend.solicitudes.client.PersonaClient;
import ar.edu.utn.frc.backend.solicitudes.client.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.solicitudes.dto.CreateSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IContenedorService;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.ISolicitudService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CrearSolicitudWorkflow {

    private final DepositoClient depositoClient;
    private final PersonaClient personaClient;
    private final IContenedorService contenedorService;
    private final ISolicitudService solicitudService;

    public void crearSolicitud(CreateSolicitudDto solicitudRequestDto) {

        // Crea, si no existen, las ubicaciones de origen y destino
        UbicacionResponseDto origen = depositoClient.crearUbicacion(
                solicitudRequestDto.getDireccionUbicacionOrigen(),
                solicitudRequestDto.getLatitudUbicacionOrigen(),
                solicitudRequestDto.getLongitudUbicacionOrigen(),
                solicitudRequestDto.getNombreCiudadUbicacionOrigen());

        UbicacionResponseDto destino = depositoClient.crearUbicacion(
                solicitudRequestDto.getDireccionUbicacionDestino(),
                solicitudRequestDto.getLatitudUbicacionDestino(),
                solicitudRequestDto.getLongitudUbicacionDestino(),
                solicitudRequestDto.getNombreCiudadUbicacionDestino());

        // Registra, si no esta registrado, el cliente
        personaClient.registrarCliente(
                solicitudRequestDto.getDocCliente(),
                solicitudRequestDto.getTipoDocCliente(),
                solicitudRequestDto.getNombreCliente(),
                solicitudRequestDto.getApellidoCliente(),
                solicitudRequestDto.getTelefonoCliente(),
                solicitudRequestDto.getEmailCliente());

        // Crea el contenedor
        Contenedor contenedor = contenedorService.crear(
                solicitudRequestDto.getAnchoContenedor(),
                solicitudRequestDto.getLargoContenedor(),
                solicitudRequestDto.getAlturaContenedor(),
                solicitudRequestDto.getPesoContenedor());

        // Crea la solicitud
        solicitudService.crear(
                origen.getIdUbicacion(),
                destino.getIdUbicacion(),
                contenedor,
                solicitudRequestDto.getDocCliente(),
                solicitudRequestDto.getTipoDocCliente());
    }
}
