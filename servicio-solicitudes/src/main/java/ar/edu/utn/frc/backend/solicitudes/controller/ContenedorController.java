package ar.edu.utn.frc.backend.solicitudes.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IContenedorService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/contenedores")
public class ContenedorController {

    private final IContenedorService contenedorService;

    @PostMapping
    public ResponseEntity<ContenedorResponseDto> crearContenedor(
            @RequestBody ContenedorRequestDto contenedorRequestDto) {

        ContenedorResponseDto contenedorCreado = contenedorService.crear(contenedorRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contenedorCreado);
    }

    @PutMapping("/{idContenedor}")
    public ResponseEntity<ContenedorResponseDto> actualizarContenedor(
            @PathVariable Long idContenedor,
            @RequestBody ContenedorRequestDto contenedorRequestDto) {

        return ResponseEntity.ok(contenedorService.actualizar(idContenedor, contenedorRequestDto));
    }

    @DeleteMapping("/{idContenedor}")
    public ResponseEntity<Void> eliminarContenedor(@PathVariable Long idContenedor) {
        contenedorService.eliminar(idContenedor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idContenedor}")
    public ResponseEntity<ContenedorResponseDto> obtenerContenedor(@PathVariable Long idContenedor) {
        return ResponseEntity.ok(contenedorService.obtenerPorId(idContenedor));
    }

    @GetMapping()
    public ResponseEntity<List<ContenedorResponseDto>> obtenerContenedores() {
        return ResponseEntity.ok(contenedorService.obtenerTodos());
    }
}
