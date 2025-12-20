package ar.edu.utn.frc.backend.depositos.controller;

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

import ar.edu.utn.frc.backend.depositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IDepositoService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/depositos")
public class DepositoController {

    private final IDepositoService depositoService;

    @PostMapping()
    public ResponseEntity<DepositoResponseDto> crearDeposito(@RequestBody DepositoRequestDto depositoRequestDto) {
        DepositoResponseDto depositoCreado = depositoService.crear(depositoRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(depositoCreado);
    }

    @PutMapping("/{idDeposito}")
    public ResponseEntity<DepositoResponseDto> actualizarDeposito(
            @PathVariable Long idDeposito,
            @RequestBody DepositoRequestDto depositoRequestDto) {
        return ResponseEntity.ok(depositoService.actualizar(idDeposito, depositoRequestDto));
    }

    @DeleteMapping("/{idDeposito}")
    public ResponseEntity<Void> eliminarDeposito(@PathVariable Long idDeposito) {
        depositoService.eliminar(idDeposito);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idDeposito}")
    public ResponseEntity<DepositoResponseDto> obtenerDepositoPorId(@PathVariable Long idDeposito) {
        return ResponseEntity.ok(depositoService.obtenerPorId(idDeposito));
    }

    @GetMapping()
    public ResponseEntity<List<DepositoResponseDto>> obtenerDepositos() {
        return ResponseEntity.ok(depositoService.obtenerTodos());
    }
}
