package ar.edu.utn.frc.backend.personas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Hidden;

import ar.edu.utn.frc.backend.personas.dto.CreateClienteDto;
import ar.edu.utn.frc.backend.personas.service.interfaces.IClienteService;
import lombok.RequiredArgsConstructor;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes/internal")
public class ClienteInternalController {

    private final IClienteService clienteService;

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @PostMapping()
    public ResponseEntity<Void> registrarCliente(@RequestBody CreateClienteDto dto) {
        clienteService.crear(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
