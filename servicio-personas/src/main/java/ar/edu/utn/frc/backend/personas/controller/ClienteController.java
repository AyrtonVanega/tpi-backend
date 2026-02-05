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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import ar.edu.utn.frc.backend.personas.dto.ClienteResponseDto;
import ar.edu.utn.frc.backend.personas.dto.CreateClienteDto;
import ar.edu.utn.frc.backend.personas.dto.PutClienteDto;
import ar.edu.utn.frc.backend.personas.service.interfaces.IClienteService;
import lombok.AllArgsConstructor;

@Tag(
    name = "Clientes", 
    description = "Operaciones de administracion sobre Clientes"
)
@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {
    
    private final IClienteService clienteService;

    @Operation(
        summary = "Registrar un cliente",
        description = "Permite registrar un nuevo cliente en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping()
    public ResponseEntity<Void> registrarCliente(@RequestBody CreateClienteDto clienteRequestDto) {
        clienteService.crear(clienteRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(
        summary = "Actualizar un cliente",
        description = "Actualiza los datos de un cliente. Requiere rol CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/{docCliente}/{tipoDocCliente}")
    public ResponseEntity<Void> actualizarCliente(
            @PathVariable String docCliente,
            @PathVariable String tipoDocCliente,
            @RequestBody PutClienteDto clienteRequestDto) {
        clienteService.actualizar(docCliente, tipoDocCliente, clienteRequestDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Dar de baja un cliente",
        description = "Elimina un cliente por su documento. Requiere rol CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente dado de baja correctamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PreAuthorize("hasRole('CLIENTE')")
    @DeleteMapping("/{docCliente}/{tipoDocCliente}")
    public ResponseEntity<Void> darDeBajaCliente(
            @PathVariable String docCliente,
            @PathVariable String tipoDocCliente) {
        clienteService.eliminar(docCliente, tipoDocCliente);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener cliente por documento",
        description = "Devuelve la información de un cliente específico. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/{docCliente}/{tipoDocCliente}")
    public ResponseEntity<ClienteResponseDto> obtenerClientePorDoc(
            @PathVariable String docCliente,
            @PathVariable String tipoDocCliente) {
        return ResponseEntity.ok(clienteService.obtenerPorId(docCliente, tipoDocCliente));
    }

    @Operation(
        summary = "Listar clientes",
        description = "Devuelve la lista completa de clientes registrados. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de clientes"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping()
    public ResponseEntity<List<ClienteResponseDto>> obtenerClientes() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }
}
