package ar.edu.utn.frc.backend.ServicioDepositos.controller;

import ar.edu.utn.frc.backend.ServicioDepositos.dto.EstadoEstadiaDepositoRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.EstadoEstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces.IEstadoEstadiaDepositoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/estados-estadias-deposito")
public class EstadoEstadiaDepositoController {

    private final IEstadoEstadiaDepositoService estadoEstadiaDepositoService;

    @PostMapping
    public ResponseEntity<EstadoEstadiaDepositoResponseDto> crearEstadoEstadia(
            @RequestBody EstadoEstadiaDepositoRequestDto dto) {
        EstadoEstadiaDepositoResponseDto estadoEstadiaDeposito = estadoEstadiaDepositoService.crear(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estadoEstadiaDeposito);
    }

    @PutMapping("/{idEstadoEstadiaDeposito}")
    public ResponseEntity<EstadoEstadiaDepositoResponseDto> actualizarEstadoEstadia(
            @PathVariable Long idEstadoEstadiaDeposito,
            @RequestBody EstadoEstadiaDepositoRequestDto dto) {
        return ResponseEntity.ok(estadoEstadiaDepositoService.actualizar(idEstadoEstadiaDeposito, dto));
    }

    @DeleteMapping("/{idEstadoEstadiaDeposito}")
    public ResponseEntity<Void> eliminarEstadoEstadia(
            @PathVariable Long idEstadoEstadiaDeposito) {
        estadoEstadiaDepositoService.eliminar(idEstadoEstadiaDeposito);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idEstadoEstadiaDeposito}")
    public ResponseEntity<EstadoEstadiaDepositoResponseDto> obtenerEstadoEstadia(
            @PathVariable Long idEstadoEstadiaDeposito) {
        return ResponseEntity.ok(estadoEstadiaDepositoService.obtenerPorId(idEstadoEstadiaDeposito));
    }

    @GetMapping
    public ResponseEntity<List<EstadoEstadiaDepositoResponseDto>> obtenerEstadoEstadia() {
        return ResponseEntity.ok(estadoEstadiaDepositoService.obtenerTodos());
    }
}
