package ar.edu.utn.frc.backend.ServicioDepositos.controller;

import ar.edu.utn.frc.backend.ServicioDepositos.dto.EstadiaDepositoRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.EstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces.IEstadiaDepositoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/estadias-deposito")
public class EstadiaDepositoController {

    private final IEstadiaDepositoService estadiaDepositoService;

    @PostMapping()
    public ResponseEntity<EstadiaDepositoResponseDto> crearEstadia(@RequestBody EstadiaDepositoRequestDto dto) {
        EstadiaDepositoResponseDto estadia = estadiaDepositoService.crear(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estadia);
    }

    //@PutMapping("/{idEstadiaDeposito}")
    //@DeleteMapping("/{idEstadiaDeposito}")
    //@GetMapping("/{idEstadiaDeposito}")

    @GetMapping()
    public ResponseEntity<List<EstadiaDepositoResponseDto>> listarEstadias() {
        return ResponseEntity.ok(estadiaDepositoService.obtenerTodos());
    }
}
