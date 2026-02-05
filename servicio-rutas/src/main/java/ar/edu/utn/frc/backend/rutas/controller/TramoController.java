package ar.edu.utn.frc.backend.rutas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import ar.edu.utn.frc.backend.rutas.dto.PatchTramoDto;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITramoService;
import ar.edu.utn.frc.backend.rutas.workflow.FinalizarTramoWorkflow;
import ar.edu.utn.frc.backend.rutas.workflow.IniciarTramoWorkflow;
import lombok.AllArgsConstructor;

@Tag(
    name = "Tramos", 
    description = "Operaciones de administracion sobre Tramos"
)
@RestController
@AllArgsConstructor
@RequestMapping("/tramos")
public class TramoController {

    private final ITramoService tramoService;
    private final IniciarTramoWorkflow iniciarTramoWorkflow;
    private final FinalizarTramoWorkflow finalizarTramoWorkflow;

    @Operation(
        summary = "Asignar camión a tramo",
        description = "Asigna un camión a un tramo de una ruta. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Camión asignado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Tramo o camión no encontrado"),
        @ApiResponse(
            responseCode = "409", 
            description = """
                Conflicto de negocio. Puede ocurrir cuando:
                - El tramo ya fue iniciado o finalizado
                - El camión no se encuentra disponible
                - El camión no tiene capacidad suficiente para el contenedor del tramo
                """
        )
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @PatchMapping("/{idRuta}/{orden}/asignar-camion")
    public ResponseEntity<Void> asignarCamionATramo(@PathVariable Long idRuta, @PathVariable int orden,
            @RequestBody PatchTramoDto dto) {

        tramoService.asignarCamion(idRuta, orden, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Iniciar tramo",
        description = "Marca el inicio de un tramo. Requiere rol TRANSPORTISTA.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Tramo iniciado correctamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(
            responseCode = "409", 
            description = """
                Conflicto de negocio. Puede ocurrir cuando:
                - El tramo anterior no fue finalizado
                - El tramo no está en estado 'ASIGNADO'
                """
        )
    })
    @PreAuthorize("hasRole('TRANSPORTISTA')")
    @PatchMapping("/{idRuta}/{orden}/iniciar")
    public ResponseEntity<Void> iniciarTramo(@PathVariable Long idRuta, @PathVariable int orden) {
        iniciarTramoWorkflow.iniciarTramo(idRuta, orden);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Finalizar tramo",
        description = "Marca el fin de un tramo. Requiere rol TRANSPORTISTA.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Tramo finalizado correctamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "409", description = "El tramo no está en estado 'INICIADO'")
    })
    @PreAuthorize("hasRole('TRANSPORTISTA')")
    @PatchMapping("/{idRuta}/{orden}/finalizar")
    public ResponseEntity<Void> finalizarTramo(@PathVariable Long idRuta, @PathVariable int orden) {
        finalizarTramoWorkflow.finalizarTramo(idRuta, orden);
        return ResponseEntity.noContent().build();
    }
}
