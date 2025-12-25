package ar.edu.utn.frc.backend.personas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.personas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.personas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.personas.dto.PatchCamionDto;
import ar.edu.utn.frc.backend.personas.service.interfaces.ICamionService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/camiones")
public class CamionController {

    private final ICamionService camionService;

    @PostMapping()
    public ResponseEntity<Void> crearCamion(@RequestBody CamionRequestDto camionRequestDto) {
        camionService.crear(camionRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{patenteCamion}")
    public ResponseEntity<Void> actualizarCamion(
            @PathVariable String patenteCamion,
            @RequestBody CamionRequestDto camionRequestDto) {

        camionService.actualizar(patenteCamion, camionRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{patenteCamion}/disponibilidad")
    public ResponseEntity<Void> actualizarDisponibilidadCamion(
            @PathVariable String patenteCamion,
            @RequestBody PatchCamionDto patchCamionDto) {

        camionService.actualizarDisponibilidad(patenteCamion, patchCamionDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{patenteCamion}")
    public ResponseEntity<Void> eliminarCamion(@PathVariable String patenteCamion) {
        camionService.eliminar(patenteCamion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{patenteCamion}")
    public ResponseEntity<CamionResponseDto> obtenerCamionPorId(@PathVariable String patenteCamion) {
        return ResponseEntity.ok(camionService.obtenerPorId(patenteCamion));
    }

    @GetMapping()
    public ResponseEntity<List<CamionResponseDto>> obtenerCamiones() {
        return ResponseEntity.ok(camionService.obtenerTodos());
    }
}
