package ar.edu.utn.frc.backend.depositos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import ar.edu.utn.frc.backend.depositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IUbicacionService;
import lombok.AllArgsConstructor;

@Tag(
    name = "Ubicaciones", 
    description = "Operaciones de administracion sobre Ubicaciones"
)
@RestController
@AllArgsConstructor
@RequestMapping("/ubicaciones")
public class UbicacionController {
    
    private final IUbicacionService ubicacionService;

    @Operation(
        summary = "Actualizar ubicación",
        description = "Actualiza una ubicación existente. Requiere rol CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Ubicación actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Ubicación no encontrada")
    })
    @PreAuthorize("hasRole('CLIENTE')")
    @PutMapping("/{idUbicacion}")
    public ResponseEntity<Void> actualizarUbicacion(
            @PathVariable Long idUbicacion,
            @RequestBody UbicacionRequestDto ubicacionRequestDto) {
        ubicacionService.actualizar(idUbicacion, ubicacionRequestDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Eliminar ubicación",
        description = "Elimina una ubicación por ID. Requiere rol CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Ubicación eliminada correctamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Ubicación no encontrada")
    })
    @PreAuthorize("hasRole('CLIENTE')")
    @DeleteMapping("/{idUbicacion}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable Long idUbicacion) {
        ubicacionService.eliminar(idUbicacion);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener ubicación por ID",
        description = "Devuelve la información de una ubicación específica. Requiere rol OPERADOR o CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ubicación encontrada"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Ubicación no encontrada")
    })
    @PreAuthorize("hasAnyRole('OPERADOR', 'CLIENTE')")
    @GetMapping("/{idUbicacion}")
    public ResponseEntity<UbicacionResponseDto> obtenerUbicacionPorId(@PathVariable Long idUbicacion) {
        return ResponseEntity.ok(ubicacionService.obtenerPorId(idUbicacion));
    }

    @Operation(
        summary = "Listar ubicaciones",
        description = "Devuelve la lista de ubicaciones. Requiere rol OPERADOR o CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de ubicaciones"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasAnyRole('OPERADOR', 'CLIENTE')")
    @GetMapping()
    public ResponseEntity<List<UbicacionResponseDto>> obtenerUbicaciones() {
        return ResponseEntity.ok(ubicacionService.obtenerTodos());
    }
}
