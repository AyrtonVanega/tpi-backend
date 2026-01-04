package ar.edu.utn.frc.backend.solicitudes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.solicitudes.dto.PatchAsignarRutadDto;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.ISolicitudService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/solicitudes/internal")
public class SolicitudInternalController {

    private final ISolicitudService solicitudService;

    @PatchMapping("/{idSolicitud}/asignar-ruta")
    public ResponseEntity<Void> asignarRuta(
            @PathVariable Long idSolicitud,
            @RequestBody PatchAsignarRutadDto asignarRutadDto) {

        solicitudService.asignarRuta(idSolicitud, asignarRutadDto);
        return ResponseEntity.noContent().build();
    }
}
