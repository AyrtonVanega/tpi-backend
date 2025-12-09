package ar.edu.utn.frc.backend.ServicioSolicitudes.controller;

import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.ContenedorRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.ContenedorResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.SolicitudRequestDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.ServicioSolicitudes.service.interfaces.IContenedorService;
import ar.edu.utn.frc.backend.ServicioSolicitudes.service.interfaces.ISolicitudService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final ISolicitudService solicitudService;

    @PostMapping
    public ResponseEntity<SolicitudResponseDto> crearSolicitud(
            @RequestBody SolicitudRequestDto solicitudRequestDto) {

        SolicitudResponseDto solicitudCreada = solicitudService.crear(solicitudRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(solicitudCreada);
    }

    @PutMapping("/{idSolicitud}")
    public ResponseEntity<SolicitudResponseDto> actualizarSolicitud(
            @PathVariable Long idSolicitud,
            @RequestBody SolicitudRequestDto solicitudRequestDto) {

        return ResponseEntity.ok(solicitudService.actualizar(idSolicitud, solicitudRequestDto));
    }

    @DeleteMapping("/{idSolicitud}")
    public ResponseEntity<Void> eliminarSolicitud(@PathVariable Long idSolicitud) {
        solicitudService.eliminar(idSolicitud);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idSolicitud}")
    public ResponseEntity<SolicitudResponseDto> obtenerSolicitud(@PathVariable Long idSolicitud) {
        return ResponseEntity.ok(solicitudService.obtenerPorId(idSolicitud));
    }

    @GetMapping()
    public ResponseEntity<List<SolicitudResponseDto>> obtenerSolicitudes() {
        return ResponseEntity.ok(solicitudService.obtenerTodos());
    }
}
