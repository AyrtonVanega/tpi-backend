package ar.edu.utn.frc.backend.depositos.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IEstadiaDepositoService;

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
