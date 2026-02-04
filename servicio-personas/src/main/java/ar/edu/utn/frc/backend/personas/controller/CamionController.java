package ar.edu.utn.frc.backend.personas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.personas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.personas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.personas.service.interfaces.ICamionService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/camiones")
public class CamionController {

    private final ICamionService camionService;

    @PreAuthorize("hasRole('OPERADOR')")
    @PutMapping("/{patenteCamion}")
    public ResponseEntity<Void> actualizarCamion(
            @PathVariable String patenteCamion,
            @RequestBody CamionRequestDto camionRequestDto) {

        camionService.actualizar(patenteCamion, camionRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/{patenteCamion}")
    public ResponseEntity<CamionResponseDto> obtenerCamionPorId(@PathVariable String patenteCamion) {
        return ResponseEntity.ok(camionService.obtenerPorId(patenteCamion));
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/disponibles")
    public ResponseEntity<List<CamionResponseDto>> obtenerCamionesDisponibles() {
        return ResponseEntity.ok(camionService.obtenerCamionesDisponibles());
    }
}
