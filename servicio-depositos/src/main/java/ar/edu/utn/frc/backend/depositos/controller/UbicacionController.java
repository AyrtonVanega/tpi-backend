package ar.edu.utn.frc.backend.depositos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.depositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IUbicacionService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/ubicaciones")
public class UbicacionController {
    
    private final IUbicacionService ubicacionService;

    @PostMapping()
    public ResponseEntity<Void> crearUbicacion(@RequestBody UbicacionRequestDto ubicacionRequestDto) {
        ubicacionService.crear(ubicacionRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/{idUbicacion}")
    public ResponseEntity<Void> actualizarUbicacion(
            @PathVariable Long idUbicacion,
            @RequestBody UbicacionRequestDto ubicacionRequestDto) {
        ubicacionService.actualizar(idUbicacion, ubicacionRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @DeleteMapping("/{idUbicacion}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable Long idUbicacion) {
        ubicacionService.eliminar(idUbicacion);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/{idUbicacion}")
    public ResponseEntity<UbicacionResponseDto> obtenerUbicacionPorId(@PathVariable Long idUbicacion) {
        return ResponseEntity.ok(ubicacionService.obtenerPorId(idUbicacion));
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping()
    public ResponseEntity<List<UbicacionResponseDto>> obtenerUbicaciones() {
        return ResponseEntity.ok(ubicacionService.obtenerTodos());
    }
}
