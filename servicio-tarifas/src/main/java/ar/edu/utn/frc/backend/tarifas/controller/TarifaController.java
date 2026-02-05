package ar.edu.utn.frc.backend.tarifas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.tarifas.dto.CreateTarifaDto;
import ar.edu.utn.frc.backend.tarifas.dto.PatchTarifaDto;
import ar.edu.utn.frc.backend.tarifas.dto.TarifaResponseDto;
import ar.edu.utn.frc.backend.tarifas.service.interfaces.ITarifaService;
import ar.edu.utn.frc.backend.tarifas.workflow.CrearTarifaWorkflow;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/tarifas")
public class TarifaController {
    
    private final ITarifaService tarifaService;
    private final CrearTarifaWorkflow crearTarifaWorkflow;

    @PreAuthorize("hasRole('OPERADOR')")
    @PostMapping()
    public ResponseEntity<Void> crearTarifa(@RequestBody CreateTarifaDto tarifaRequestDto) {
        crearTarifaWorkflow.crearTarifa(tarifaRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @PatchMapping("/{idTarifa}")
    public ResponseEntity<Void> actualizarTarifa(
            @PathVariable Long idTarifa,
            @RequestBody PatchTarifaDto tarifaRequestDto) {
        tarifaService.actualizarParcial(idTarifa, tarifaRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @DeleteMapping("/{idTarifa}")
    public ResponseEntity<Void> eliminarTarifa(@PathVariable Long idTarifa) {
        tarifaService.eliminar(idTarifa);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/{idTarifa}")
    public ResponseEntity<TarifaResponseDto> obtenerTarifaPorId(@PathVariable Long idTarifa) {
        return ResponseEntity.ok(tarifaService.obtenerPorId(idTarifa));
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping()
    public ResponseEntity<List<TarifaResponseDto>> obtenerTarifas() {
        return ResponseEntity.ok(tarifaService.obtenerTodos());
    }
}
