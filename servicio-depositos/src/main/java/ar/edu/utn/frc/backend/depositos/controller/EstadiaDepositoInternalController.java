package ar.edu.utn.frc.backend.depositos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.depositos.dto.PatchEstadiaDepositoDto;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IEstadiaDepositoService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/estadias-deposito/internal")
public class EstadiaDepositoInternalController {

    private final IEstadiaDepositoService estadiaDepositoService;

    @PatchMapping("/{idDeposito}/{idSolicitud}")
    public ResponseEntity<Void> finalizarEstadia(
            @PathVariable Long idDeposito,
            @PathVariable Long idSolicitud,
            @RequestBody PatchEstadiaDepositoDto dto) {

        estadiaDepositoService.finalizarEstadia(idDeposito, idSolicitud, dto);
        return ResponseEntity.noContent().build();
    }
}
