package ar.edu.utn.frc.backend.depositos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.depositos.dto.CreateEstadiaDepositoDto;
import ar.edu.utn.frc.backend.depositos.dto.PatchEstadiaDepositoDto;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IEstadiaDepositoService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/estadias-deposito/internal")
public class EstadiaDepositoInternalController {

    private final IEstadiaDepositoService estadiaDepositoService;

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @PostMapping()
    public ResponseEntity<Void> crearEstadia(@RequestBody CreateEstadiaDepositoDto dto) {
        estadiaDepositoService.crear(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @PatchMapping("/{idDeposito}/{idSolicitud}")
    public ResponseEntity<Void> finalizarEstadia(
            @PathVariable Long idDeposito,
            @PathVariable Long idSolicitud,
            @RequestBody PatchEstadiaDepositoDto dto) {

        estadiaDepositoService.finalizarEstadia(idDeposito, idSolicitud, dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @GetMapping("/costo-total")
    public ResponseEntity<Double> calcularCostoEstadiaDiario(@RequestParam Long idSolicitud) {
        return ResponseEntity.ok(estadiaDepositoService.calcularCostoTotal(idSolicitud));
    }
}
