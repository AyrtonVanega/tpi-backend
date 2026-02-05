package ar.edu.utn.frc.backend.solicitudes.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PutContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SeguimientoContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IContenedorService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/contenedores")
public class ContenedorController {

    private final IContenedorService contenedorService;

    @PreAuthorize("hasAnyRole('OPERADOR','CLIENTE')")
    @PutMapping("/{idContenedor}")
    public ResponseEntity<Void> actualizarContenedor(
            @PathVariable Long idContenedor,
            @RequestBody PutContenedorDto contenedorRequestDto) {

        contenedorService.actualizar(idContenedor, contenedorRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('OPERADOR','CLIENTE')")
    @GetMapping("/{idContenedor}")
    public ResponseEntity<ContenedorResponseDto> obtenerContenedor(@PathVariable Long idContenedor) {
        return ResponseEntity.ok(contenedorService.obtenerPorId(idContenedor));
    }

    @PreAuthorize("hasAnyRole('OPERADOR','CLIENTE')")
    @GetMapping()
    public ResponseEntity<List<ContenedorResponseDto>> obtenerContenedores() {
        return ResponseEntity.ok(contenedorService.obtenerTodos());
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{idContenedor}/estados")
    public ResponseEntity<SeguimientoContenedorDto> obtenerEstadosContenedor(@PathVariable Long idContenedor) {
        return ResponseEntity.ok(contenedorService.obtenerEstadosContenedor(idContenedor));
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/pendientes")
    public ResponseEntity<List<ContenedorResponseDto>> obtenerContenedoresPendientes(
            @RequestParam String estado) {

        return ResponseEntity.ok(contenedorService.obtenerContenedoresPendientes(estado));
    }
}
