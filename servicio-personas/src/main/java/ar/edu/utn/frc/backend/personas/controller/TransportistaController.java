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

import ar.edu.utn.frc.backend.personas.client.dto.TramoResponseDto;
import ar.edu.utn.frc.backend.personas.dto.CreateTransportistaDto;
import ar.edu.utn.frc.backend.personas.dto.PutTransportistaDto;
import ar.edu.utn.frc.backend.personas.dto.TransportistaResponseDto;
import ar.edu.utn.frc.backend.personas.service.interfaces.ITransportistaService;
import ar.edu.utn.frc.backend.personas.workflow.ConsultarTramosAsignadosWorkflow;
import ar.edu.utn.frc.backend.personas.workflow.RegistrarTransportistaWorkflow;
import lombok.AllArgsConstructor;

@Tag(
    name = "Transportistas", 
    description = "Operaciones de administracion sobre Transportistas"
)
@RestController
@AllArgsConstructor
@RequestMapping("/transportistas")
public class TransportistaController {

    private final ITransportistaService transportistaService;
    private final ConsultarTramosAsignadosWorkflow consultarTramosAsignadosWorkflow;
    private final RegistrarTransportistaWorkflow registrarTransportistaWorkflow;

    @Operation(
        summary = "Registrar un transportista",
        description = "Permite registrar un nuevo transportista en el sistema. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Transportista creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @PostMapping()
    public ResponseEntity<Void> registrarTransportista(@RequestBody CreateTransportistaDto transportistaRequestDto) {
        registrarTransportistaWorkflow.registrarTransportista(transportistaRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(
        summary = "Actualizar un transportista",
        description = "Actualiza los datos de un transportista identificado por su documento. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Transportista actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Transportista no encontrado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @PutMapping("/{docTransportista}/{tipoDocTransportista}")
    public ResponseEntity<Void> actualizarTransportista(
            @PathVariable String docTransportista,
            @PathVariable String tipoDocTransportista,
            @RequestBody PutTransportistaDto transportistaRequestDto) {
        transportistaService.actualizar(docTransportista, tipoDocTransportista, transportistaRequestDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Dar de baja un transportista",
        description = "Elimina un transportista del sistema por su documento. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Transportista dado de baja correctamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Transportista no encontrado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @DeleteMapping("/{docTransportista}/{tipoDocTransportista}")
    public ResponseEntity<Void> darDeBajaTransportista(
            @PathVariable String docTransportista,
            @PathVariable String tipoDocTransportista) {
        transportistaService.eliminar(docTransportista, tipoDocTransportista);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener transportista por documento",
        description = "Devuelve la información de un transportista específico. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transportista encontrado"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Transportista no encontrado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/{docTransportista}/{tipoDocTransportista}")
    public ResponseEntity<TransportistaResponseDto> obtenerTransportistaPorDoc(
            @PathVariable String docTransportista,
            @PathVariable String tipoDocTransportista) {
        return ResponseEntity.ok(transportistaService.obtenerPorId(docTransportista, tipoDocTransportista));
    }

    @Operation(
        summary = "Listar transportistas",
        description = "Devuelve la lista completa de transportistas registrados. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de transportistas"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping()
    public ResponseEntity<List<TransportistaResponseDto>> obtenerTransportistas() {
        return ResponseEntity.ok(transportistaService.obtenerTodos());
    }

    @Operation(
        summary = "Obtener tramos asignados",
        description = "Devuelve los tramos asignados a un transportista. Requiere rol TRANSPORTISTA.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de tramos asignados"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('TRANSPORTISTA')")
    @GetMapping("/{docTransportista}/{tipoDocTransportista}/tramos-asignados")
    public ResponseEntity<List<TramoResponseDto>> obtenerTramosAsignados(
            @PathVariable String docTransportista,
            @PathVariable String tipoDocTransportista) {

        return ResponseEntity
                .ok(consultarTramosAsignadosWorkflow.consultarTramosAsignados(docTransportista, tipoDocTransportista));
    }
}
