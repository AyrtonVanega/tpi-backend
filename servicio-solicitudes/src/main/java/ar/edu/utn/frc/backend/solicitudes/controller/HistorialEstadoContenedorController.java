package ar.edu.utn.frc.backend.solicitudes.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.backend.solicitudes.dto.HistorialEstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.HistorialEstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IHistorialEstadoContenedorService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/historiales-estado-contenedor")
public class HistorialEstadoContenedorController {

    private final IHistorialEstadoContenedorService historialService;

    @PostMapping
    public ResponseEntity<HistorialEstadoContenedorResponseDto> crearHistorialEstadoContenedor(
            @RequestBody HistorialEstadoContenedorRequestDto historialRequestDto) {

        HistorialEstadoContenedorResponseDto historialCreado = historialService.crear(historialRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(historialCreado);
    }

    @PutMapping("/{idHistorial}")
    public ResponseEntity<HistorialEstadoContenedorResponseDto> actualizarHistorialEstadoContenedor(
            @PathVariable Long idHistorial,
            @RequestBody HistorialEstadoContenedorRequestDto historialRequestDto) {

        return ResponseEntity.ok(historialService.actualizar(idHistorial, historialRequestDto));
    }

    @DeleteMapping("/{idHistorial}")
    public ResponseEntity<Void> eliminarHistorialEstadoContenedor(@PathVariable Long idHistorial) {
        historialService.eliminar(idHistorial);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idHistorial}")
    public ResponseEntity<HistorialEstadoContenedorResponseDto> obtenerHistorialEstadoContenedor(
            @PathVariable Long idHistorial) {
        return ResponseEntity.ok(historialService.obtenerPorId(idHistorial));
    }

    @GetMapping()
    public ResponseEntity<List<HistorialEstadoContenedorResponseDto>> obtenerHistorialesEstadoContenedor() {
        return ResponseEntity.ok(historialService.obtenerTodos());
    }
}
