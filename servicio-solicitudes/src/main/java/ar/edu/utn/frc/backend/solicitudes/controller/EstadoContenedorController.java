package ar.edu.utn.frc.backend.solicitudes.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.backend.solicitudes.dto.EstadoContenedorRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.EstadoContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IEstadoContenedorService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/estados-contenedor")
public class EstadoContenedorController {

    private final IEstadoContenedorService estadoContenedorService;

    @PostMapping
    public ResponseEntity<EstadoContenedorResponseDto> crearEstadoContenedor(
            @RequestBody EstadoContenedorRequestDto estadoContenedorRequestDto) {

        EstadoContenedorResponseDto estadoContenedorCreado = estadoContenedorService.crear(estadoContenedorRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estadoContenedorCreado);
    }

    @PutMapping("/{idEstadoContenedor}")
    public ResponseEntity<EstadoContenedorResponseDto> actualizarEstadoContenedor(
            @PathVariable Long idEstadoContenedor,
            @RequestBody EstadoContenedorRequestDto estadoContenedorRequestDto) {

        return ResponseEntity.ok(estadoContenedorService.actualizar(idEstadoContenedor, estadoContenedorRequestDto));
    }

    @DeleteMapping("/{idEstadoContenedor}")
    public ResponseEntity<Void> eliminarEstadoContenedor(@PathVariable Long idEstadoContenedor) {
        estadoContenedorService.eliminar(idEstadoContenedor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idEstadoContenedor}")
    public ResponseEntity<EstadoContenedorResponseDto> obtenerEstadoContenedor(@PathVariable Long idEstadoContenedor) {
        return ResponseEntity.ok(estadoContenedorService.obtenerPorId(idEstadoContenedor));
    }

    @GetMapping()
    public ResponseEntity<List<EstadoContenedorResponseDto>> obtenerEstadosContenedor() {
        return ResponseEntity.ok(estadoContenedorService.obtenerTodos());
    }
}
