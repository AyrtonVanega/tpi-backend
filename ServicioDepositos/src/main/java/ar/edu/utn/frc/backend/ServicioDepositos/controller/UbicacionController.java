package ar.edu.utn.frc.backend.ServicioDepositos.controller;

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

import ar.edu.utn.frc.backend.ServicioDepositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces.IUbicacionService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ubicaciones")
public class UbicacionController {
    
    private final IUbicacionService ubicacionService;

    @PostMapping()
    public ResponseEntity<UbicacionResponseDto> crearUbicacion(@RequestBody UbicacionRequestDto ubicacionRequestDto) {
        UbicacionResponseDto ubicacionCreada = ubicacionService.crear(ubicacionRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ubicacionCreada);
    }

    @PutMapping("/{idUbicacion}")
    public ResponseEntity<UbicacionResponseDto> actualizarUbicacion(
            @PathVariable Long idUbicacion,
            @RequestBody UbicacionRequestDto ubicacionRequestDto) {
        return ResponseEntity.ok(ubicacionService.actualizar(idUbicacion, ubicacionRequestDto));
    }

    @DeleteMapping("/{idUbicacion}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable Long idUbicacion) {
        ubicacionService.eliminar(idUbicacion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idUbicacion}")
    public ResponseEntity<UbicacionResponseDto> obtenerUbicacionPorId(@PathVariable Long idUbicacion) {
        return ResponseEntity.ok(ubicacionService.obtenerPorId(idUbicacion));
    }

    @GetMapping()
    public ResponseEntity<List<UbicacionResponseDto>> obtenerUbicaciones() {
        return ResponseEntity.ok(ubicacionService.obtenerTodos());
    }
}
