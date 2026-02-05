package ar.edu.utn.frc.backend.solicitudes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Hidden;

import ar.edu.utn.frc.backend.solicitudes.dto.FinalizarSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PatchAsignarRutadDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PatchSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.ISolicitudService;
import lombok.AllArgsConstructor;

@Hidden
@RestController
@AllArgsConstructor
@RequestMapping("/solicitudes/internal")
public class SolicitudInternalController {

    private final ISolicitudService solicitudService;

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @PatchMapping("/{idSolicitud}/asignar-ruta")
    public ResponseEntity<Void> asignarRuta(
            @PathVariable Long idSolicitud,
            @RequestBody PatchAsignarRutadDto asignarRutadDto) {

        solicitudService.asignarRuta(idSolicitud, asignarRutadDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @PatchMapping("/{idSolicitud}/estado")
    public ResponseEntity<Void> actualizarEstadoSolicitud(
            @PathVariable Long idSolicitud,
            @RequestBody PatchSolicitudDto solicitudRequestDto) {

        solicitudService.actualizarEstado(idSolicitud, solicitudRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @PatchMapping("/{idSolicitud}/finalizar")
    public ResponseEntity<Void> finalizarSolicitud(
            @PathVariable Long idSolicitud,
            @RequestBody FinalizarSolicitudDto solicitudRequestDto) {

        solicitudService.finalizarSolicitud(idSolicitud, solicitudRequestDto);
        return ResponseEntity.noContent().build();
    }
}
