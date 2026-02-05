package ar.edu.utn.frc.backend.solicitudes.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.dto.PutContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.dto.SeguimientoContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IContenedorService;

import java.util.List;

@Tag(
    name = "Contenedores", 
    description = "Operaciones de administracion sobre Contenedores"
)
@RestController
@AllArgsConstructor
@RequestMapping("/contenedores")
public class ContenedorController {

    private final IContenedorService contenedorService;

    @Operation(
        summary = "Actualizar contenedor",
        description = "Actualiza los datos de un contenedor. Requiere rol OPERADOR o CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Contenedor actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Contenedor no encontrado")
    })
    @PreAuthorize("hasAnyRole('OPERADOR','CLIENTE')")
    @PutMapping("/{idContenedor}")
    public ResponseEntity<Void> actualizarContenedor(
            @PathVariable Long idContenedor,
            @RequestBody PutContenedorDto contenedorRequestDto) {

        contenedorService.actualizar(idContenedor, contenedorRequestDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener contenedor por ID",
        description = "Devuelve la información de un contenedor. Requiere rol OPERADOR o CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contenedor encontrado"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Contenedor no encontrado")
    })
    @PreAuthorize("hasAnyRole('OPERADOR','CLIENTE')")
    @GetMapping("/{idContenedor}")
    public ResponseEntity<ContenedorResponseDto> obtenerContenedor(@PathVariable Long idContenedor) {
        return ResponseEntity.ok(contenedorService.obtenerPorId(idContenedor));
    }

    @Operation(
        summary = "Listar contenedores",
        description = "Devuelve la lista de contenedores. Requiere rol OPERADOR o CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de contenedores"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasAnyRole('OPERADOR','CLIENTE')")
    @GetMapping()
    public ResponseEntity<List<ContenedorResponseDto>> obtenerContenedores() {
        return ResponseEntity.ok(contenedorService.obtenerTodos());
    }

    @Operation(
        summary = "Obtener estados de contenedor",
        description = "Devuelve el seguimiento de estados de un contenedor. Requiere rol CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estados obtenidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Contenedor no encontrado")
    })
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{idContenedor}/estados")
    public ResponseEntity<SeguimientoContenedorDto> obtenerEstadosContenedor(@PathVariable Long idContenedor) {
        return ResponseEntity.ok(contenedorService.obtenerEstadosContenedor(idContenedor));
    }

    @Operation(
        summary = "Listar contenedores pendientes",
        description = "Devuelve contenedores pendientes por estado. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de contenedores pendientes"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/pendientes")
    public ResponseEntity<List<ContenedorResponseDto>> obtenerContenedoresPendientes(
            @RequestParam String estado) {

        return ResponseEntity.ok(contenedorService.obtenerContenedoresPendientes(estado));
    }
}
