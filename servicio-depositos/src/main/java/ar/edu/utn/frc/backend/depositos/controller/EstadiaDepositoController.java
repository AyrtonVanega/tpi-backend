package ar.edu.utn.frc.backend.depositos.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IEstadiaDepositoService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/estadias-deposito")
public class EstadiaDepositoController {

    private final IEstadiaDepositoService estadiaDepositoService;

    @GetMapping("/{idDeposito}/{idSolicitud}")
    public ResponseEntity<EstadiaDepositoResponseDto> obtenerEstadiaPorId(
            @PathVariable Long idDeposito,
            @PathVariable Long idSolicitud) {
        return ResponseEntity.ok(estadiaDepositoService.obtenerPorId(idDeposito, idSolicitud));
    }

    @GetMapping("/activas")
    public ResponseEntity<List<EstadiaDepositoResponseDto>> listarEstadiasActivas(@RequestParam Long idDeposito) {
        return ResponseEntity.ok(estadiaDepositoService.obtenerEstadiasActivas(idDeposito));
    }
}
