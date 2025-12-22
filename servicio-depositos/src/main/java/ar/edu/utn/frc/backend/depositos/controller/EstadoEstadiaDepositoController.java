package ar.edu.utn.frc.backend.depositos.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.backend.depositos.dto.EstadoEstadiaDepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.EstadoEstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IEstadoEstadiaDepositoService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/estados-estadias-deposito")
public class EstadoEstadiaDepositoController {

    private final IEstadoEstadiaDepositoService estadoEstadiaDepositoService;

    @PreAuthorize("hasRole('OPERADOR')")
    @PostMapping
    public ResponseEntity<EstadoEstadiaDepositoResponseDto> crearEstadoEstadia(
            @RequestBody EstadoEstadiaDepositoRequestDto dto) {
        EstadoEstadiaDepositoResponseDto estadoEstadiaDeposito = estadoEstadiaDepositoService.crear(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estadoEstadiaDeposito);
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @PutMapping("/{idEstadoEstadiaDeposito}")
    public ResponseEntity<EstadoEstadiaDepositoResponseDto> actualizarEstadoEstadia(
            @PathVariable Long idEstadoEstadiaDeposito,
            @RequestBody EstadoEstadiaDepositoRequestDto dto) {
        return ResponseEntity.ok(estadoEstadiaDepositoService.actualizar(idEstadoEstadiaDeposito, dto));
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @DeleteMapping("/{idEstadoEstadiaDeposito}")
    public ResponseEntity<Void> eliminarEstadoEstadia(
            @PathVariable Long idEstadoEstadiaDeposito) {
        estadoEstadiaDepositoService.eliminar(idEstadoEstadiaDeposito);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/{idEstadoEstadiaDeposito}")
    public ResponseEntity<EstadoEstadiaDepositoResponseDto> obtenerEstadoEstadia(
            @PathVariable Long idEstadoEstadiaDeposito) {
        return ResponseEntity.ok(estadoEstadiaDepositoService.obtenerPorId(idEstadoEstadiaDeposito));
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping
    public ResponseEntity<List<EstadoEstadiaDepositoResponseDto>> obtenerEstadoEstadia() {
        return ResponseEntity.ok(estadoEstadiaDepositoService.obtenerTodos());
    }
}
