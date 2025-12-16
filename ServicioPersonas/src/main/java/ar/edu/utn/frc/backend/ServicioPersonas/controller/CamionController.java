package ar.edu.utn.frc.backend.ServicioPersonas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.ServicioPersonas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.ServicioPersonas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.ServicioPersonas.service.interfaces.ICamionService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/camiones")
public class CamionController {

    private final ICamionService camionService;

    @PostMapping()
    public ResponseEntity<CamionResponseDto> crearCamion(@RequestBody CamionRequestDto camionRequestDto) {
        CamionResponseDto camionCreado = camionService.crear(camionRequestDto);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(camionCreado);
    }

    @PutMapping("/{idCamion}")
    public ResponseEntity<CamionResponseDto> actualizarCamion(
            @PathVariable String idCamion,
            @RequestBody CamionRequestDto camionRequestDto) {
        return ResponseEntity.ok(camionService.actualizar(idCamion, camionRequestDto));
    }

    @DeleteMapping("/{idCamion}")
    public ResponseEntity<Void> eliminarCamion(@PathVariable String idCamion) {
        camionService.eliminar(idCamion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idCamion}")
    public ResponseEntity<CamionResponseDto> obtenerCamionPorId(@PathVariable String idCamion) {
        return ResponseEntity.ok(camionService.obtenerPorId(idCamion));
    }

    @GetMapping()
    public ResponseEntity<List<CamionResponseDto>> obtenerCamiones() {
        return ResponseEntity.ok(camionService.obtenerTodos());
    }
}
