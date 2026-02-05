package ar.edu.utn.frc.backend.tarifas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import ar.edu.utn.frc.backend.tarifas.dto.CreateTarifaDto;
import ar.edu.utn.frc.backend.tarifas.dto.PatchTarifaDto;
import ar.edu.utn.frc.backend.tarifas.dto.TarifaResponseDto;
import ar.edu.utn.frc.backend.tarifas.service.interfaces.ITarifaService;
import ar.edu.utn.frc.backend.tarifas.workflow.CrearTarifaWorkflow;
import lombok.AllArgsConstructor;

@Tag(
    name = "Tarifas", 
    description = "Operaciones de administracion sobre Tarifas"
)
@RestController
@AllArgsConstructor
@RequestMapping("/tarifas")
public class TarifaController {
    
    private final ITarifaService tarifaService;
    private final CrearTarifaWorkflow crearTarifaWorkflow;

    @Operation(
        summary = "Crear una tarifa",
        description = "Registra una nueva tarifa en el sistema. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Tarifa creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(
            responseCode = "409",
            description = """
                No se puede crear la tarifa debido a un 
                solapamiento entre rangos con una tarifa existente.
                """
        )
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @PostMapping()
    public ResponseEntity<Void> crearTarifa(@RequestBody CreateTarifaDto tarifaRequestDto) {
        crearTarifaWorkflow.crearTarifa(tarifaRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(
        summary = "Actualizar una tarifa",
        description = "Actualiza parcialmente una tarifa. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Tarifa actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Tarifa no encontrada")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @PatchMapping("/{idTarifa}")
    public ResponseEntity<Void> actualizarTarifa(
            @PathVariable Long idTarifa,
            @RequestBody PatchTarifaDto tarifaRequestDto) {
        tarifaService.actualizarParcial(idTarifa, tarifaRequestDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Eliminar una tarifa",
        description = "Elimina una tarifa por su ID. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Tarifa eliminada correctamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Tarifa no encontrada")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @DeleteMapping("/{idTarifa}")
    public ResponseEntity<Void> eliminarTarifa(@PathVariable Long idTarifa) {
        tarifaService.eliminar(idTarifa);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener tarifa por ID",
        description = "Devuelve la información de una tarifa específica. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tarifa encontrada"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Tarifa no encontrada")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/{idTarifa}")
    public ResponseEntity<TarifaResponseDto> obtenerTarifaPorId(@PathVariable Long idTarifa) {
        return ResponseEntity.ok(tarifaService.obtenerPorId(idTarifa));
    }

    @Operation(
        summary = "Listar tarifas",
        description = "Devuelve la lista completa de tarifas registradas. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de tarifas"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping()
    public ResponseEntity<List<TarifaResponseDto>> obtenerTarifas() {
        return ResponseEntity.ok(tarifaService.obtenerTodos());
    }
}
