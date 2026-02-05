package ar.edu.utn.frc.backend.solicitudes.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.backend.solicitudes.dto.CreateSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.ISolicitudService;
import ar.edu.utn.frc.backend.solicitudes.workflow.CrearSolicitudWorkflow;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final ISolicitudService solicitudService;
    private final CrearSolicitudWorkflow crearSolicitudWorkflow;

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping()
    public ResponseEntity<Void> crearSolicitud(@RequestBody CreateSolicitudDto solicitudRequestDto) {
        crearSolicitudWorkflow.crearSolicitud(solicitudRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PreAuthorize("hasAnyRole('OPERADOR','CLIENTE')")
    @GetMapping("/{idSolicitud}")
    public ResponseEntity<SolicitudResponseDto> obtenerSolicitud(@PathVariable Long idSolicitud) {
        return ResponseEntity.ok(solicitudService.obtenerPorId(idSolicitud));
    }

    @PreAuthorize("hasAnyRole('OPERADOR','CLIENTE')")
    @GetMapping()
    public ResponseEntity<List<SolicitudResponseDto>> obtenerSolicitudes() {
        return ResponseEntity.ok(solicitudService.obtenerTodos());
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @PatchMapping("/{idSolicitud}/cancelar")
    public ResponseEntity<Void> cancelarSolicitud(@PathVariable Long idSolicitud) {
        solicitudService.cancelarSolicitud(idSolicitud);
        return ResponseEntity.noContent().build();
    }
}
