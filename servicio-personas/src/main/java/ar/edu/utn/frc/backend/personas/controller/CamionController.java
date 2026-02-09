package ar.edu.utn.frc.backend.personas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import ar.edu.utn.frc.backend.personas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.personas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.personas.service.interfaces.ICamionService;
import ar.edu.utn.frc.backend.personas.workflow.ActualizarCamionWorkflow;
import lombok.AllArgsConstructor;

@Tag(
    name = "Camiones", 
    description = "Operaciones de administracion sobre Camiones"
)
@RestController
@AllArgsConstructor
@RequestMapping("/camiones")
public class CamionController {

    private final ICamionService camionService;
    private final ActualizarCamionWorkflow actualizarCamionWorkflow;

    @Operation(
        summary = "Actualizar camión",
        description = "Actualiza los datos de un camión identificado por su patente. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Camión actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Camión no encontrado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @PutMapping("/{patenteCamion}")
    public ResponseEntity<Void> actualizarCamion(
            @PathVariable String patenteCamion,
            @RequestBody CamionRequestDto camionRequestDto) {

        actualizarCamionWorkflow.actualizarCamion(patenteCamion, camionRequestDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener camión por patente",
        description = "Devuelve la información de un camión específico. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Camión encontrado"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Camión no encontrado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/{patenteCamion}")
    public ResponseEntity<CamionResponseDto> obtenerCamionPorId(@PathVariable String patenteCamion) {
        return ResponseEntity.ok(camionService.obtenerPorId(patenteCamion));
    }

    @Operation(
        summary = "Listar camiones disponibles",
        description = "Devuelve la lista de camiones disponibles. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de camiones disponibles"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/disponibles")
    public ResponseEntity<List<CamionResponseDto>> obtenerCamionesDisponibles() {
        return ResponseEntity.ok(camionService.obtenerCamionesDisponibles());
    }
}
