package ar.edu.utn.frc.backend.depositos.controller;

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

import ar.edu.utn.frc.backend.depositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IDepositoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(
    name = "Depositos", 
    description = "Operaciones de administracion sobre Depositos"
)
@RestController
@AllArgsConstructor
@RequestMapping("/depositos")
public class DepositoController {

    private final IDepositoService depositoService;

    @Operation(
        summary = "Crear un depósito",
        description = "Permite registrar un nuevo depósito en el sistema. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Depósito creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @PostMapping()
    public ResponseEntity<Void> crearDeposito(@RequestBody DepositoRequestDto depositoRequestDto) {
        depositoService.crear(depositoRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(
        summary = "Actualizar un depósito",
        description = "Actualiza los datos de un depósito existente identificado por su ID. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Depósito actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Depósito no encontrado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @PutMapping("/{idDeposito}")
    public ResponseEntity<Void> actualizarDeposito(
            @PathVariable Long idDeposito,
            @RequestBody DepositoRequestDto depositoRequestDto) {
        depositoService.actualizar(idDeposito, depositoRequestDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Eliminar un depósito",
        description = "Elimina un depósito del sistema por su ID. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Depósito eliminado correctamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Depósito no encontrado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @DeleteMapping("/{idDeposito}")
    public ResponseEntity<Void> eliminarDeposito(@PathVariable Long idDeposito) {
        depositoService.eliminar(idDeposito);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener depósito por ID",
        description = "Devuelve la información de un depósito específico. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Depósito encontrado"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Depósito no encontrado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/{idDeposito}")
    public ResponseEntity<DepositoResponseDto> obtenerDepositoPorId(@PathVariable Long idDeposito) {
        return ResponseEntity.ok(depositoService.obtenerPorId(idDeposito));
    }

    @Operation(
        summary = "Listar todos los depósitos",
        description = "Devuelve la lista completa de depósitos registrados. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de depósitos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping()
    public ResponseEntity<List<DepositoResponseDto>> obtenerDepositos() {
        return ResponseEntity.ok(depositoService.obtenerTodos());
    }
}
