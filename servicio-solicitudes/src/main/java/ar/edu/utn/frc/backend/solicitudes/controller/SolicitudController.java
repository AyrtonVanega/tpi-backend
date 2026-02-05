package ar.edu.utn.frc.backend.solicitudes.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import ar.edu.utn.frc.backend.solicitudes.dto.CreateSolicitudDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.ISolicitudService;
import ar.edu.utn.frc.backend.solicitudes.workflow.CrearSolicitudWorkflow;

import java.util.List;

@Tag(
    name = "Solicitudes", 
    description = "Operaciones de administracion sobre Solicitudes"
)
@RestController
@AllArgsConstructor
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final ISolicitudService solicitudService;
    private final CrearSolicitudWorkflow crearSolicitudWorkflow;

    @Operation(
        summary = "Crear una solicitud",
        description = "Permite crear una nueva solicitud. Requiere rol CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Solicitud creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping()
    public ResponseEntity<Void> crearSolicitud(@RequestBody CreateSolicitudDto solicitudRequestDto) {
        crearSolicitudWorkflow.crearSolicitud(solicitudRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(
        summary = "Obtener solicitud por ID",
        description = "Devuelve la información de una solicitud específica. Requiere rol OPERADOR o CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Solicitud encontrada"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @PreAuthorize("hasAnyRole('OPERADOR','CLIENTE')")
    @GetMapping("/{idSolicitud}")
    public ResponseEntity<SolicitudResponseDto> obtenerSolicitud(@PathVariable Long idSolicitud) {
        return ResponseEntity.ok(solicitudService.obtenerPorId(idSolicitud));
    }

    @Operation(
        summary = "Listar solicitudes",
        description = "Devuelve la lista completa de solicitudes. Requiere rol OPERADOR o CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de solicitudes"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasAnyRole('OPERADOR','CLIENTE')")
    @GetMapping()
    public ResponseEntity<List<SolicitudResponseDto>> obtenerSolicitudes() {
        return ResponseEntity.ok(solicitudService.obtenerTodos());
    }

    @Operation(
        summary = "Cancelar solicitud",
        description = "Cancela una solicitud en estado BORRADOR. Requiere rol CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Solicitud cancelada correctamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Solicitud no encontrada"),
        @ApiResponse(responseCode = "409", description = "La solicitud no está en estado 'BORRADOR'")
    })
    @PreAuthorize("hasRole('CLIENTE')")
    @PatchMapping("/{idSolicitud}/cancelar")
    public ResponseEntity<Void> cancelarSolicitud(@PathVariable Long idSolicitud) {
        solicitudService.cancelarSolicitud(idSolicitud);
        return ResponseEntity.noContent().build();
    }
}
