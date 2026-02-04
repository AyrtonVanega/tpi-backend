package ar.edu.utn.frc.backend.personas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.personas.dto.ClienteResponseDto;
import ar.edu.utn.frc.backend.personas.dto.CreateClienteDto;
import ar.edu.utn.frc.backend.personas.dto.PutClienteDto;
import ar.edu.utn.frc.backend.personas.service.interfaces.IClienteService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {
    
    private final IClienteService clienteService;

    @PostMapping()
    public ResponseEntity<Void> registrarCliente(@RequestBody CreateClienteDto clienteRequestDto) {
        clienteService.crear(clienteRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/{docCliente}/{tipoDocCliente}")
    public ResponseEntity<Void> actualizarCliente(
            @PathVariable String docCliente,
            @PathVariable String tipoDocCliente,
            @RequestBody PutClienteDto clienteRequestDto) {
        clienteService.actualizar(docCliente, tipoDocCliente, clienteRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @DeleteMapping("/{docCliente}/{tipoDocCliente}")
    public ResponseEntity<Void> darDeBajaCliente(
            @PathVariable String docCliente,
            @PathVariable String tipoDocCliente) {
        clienteService.eliminar(docCliente, tipoDocCliente);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/{docCliente}/{tipoDocCliente}")
    public ResponseEntity<ClienteResponseDto> obtenerClientePorDoc(
            @PathVariable String docCliente,
            @PathVariable String tipoDocCliente) {
        return ResponseEntity.ok(clienteService.obtenerPorId(docCliente, tipoDocCliente));
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping()
    public ResponseEntity<List<ClienteResponseDto>> obtenerClientes() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }
}
