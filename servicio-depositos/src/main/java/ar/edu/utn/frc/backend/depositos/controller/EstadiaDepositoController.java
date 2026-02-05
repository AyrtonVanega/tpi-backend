package ar.edu.utn.frc.backend.depositos.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IEstadiaDepositoService;

import java.util.List;

@Tag(
    name = "EstadiasDeposito", 
    description = "Operaciones de administracion sobre Estadias de Deposito"
)
@RestController
@AllArgsConstructor
@RequestMapping("/estadias-deposito")
public class EstadiaDepositoController {

    private final IEstadiaDepositoService estadiaDepositoService;

    @Operation(
        summary = "Obtener estadía por ID",
        description = "Devuelve la información de una estadía de depósito específico. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estadía encontrada"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Estadía no encontrada")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/{idDeposito}/{idSolicitud}")
    public ResponseEntity<EstadiaDepositoResponseDto> obtenerEstadiaPorId(
            @PathVariable Long idDeposito,
            @PathVariable Long idSolicitud) {
        return ResponseEntity.ok(estadiaDepositoService.obtenerPorId(idDeposito, idSolicitud));
    }

    @Operation(
        summary = "Listar estadías activas",
        description = "Devuelve la lista de estadías activas para un depósito. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de estadías activas"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/activas")
    public ResponseEntity<List<EstadiaDepositoResponseDto>> listarEstadiasActivas(@RequestParam Long idDeposito) {
        return ResponseEntity.ok(estadiaDepositoService.obtenerEstadiasActivas(idDeposito));
    }
}
