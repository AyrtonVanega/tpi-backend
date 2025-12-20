package ar.edu.utn.frc.backend.solicitudes.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.ISolicitudService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/solicitudes")
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
