package ar.edu.utn.frc.backend.tarifas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import ar.edu.utn.frc.backend.tarifas.dto.ParametroRequestDto;
import ar.edu.utn.frc.backend.tarifas.dto.ParametroResponseDto;
import ar.edu.utn.frc.backend.tarifas.service.interfaces.IParametroGlobalService;
import lombok.AllArgsConstructor;

@Tag(
    name = "ParametroGlobal", 
    description = "Operaciones sobre parámetros globales de tarifas"
)
@RestController
@AllArgsConstructor
@RequestMapping("/parametro-global")
public class ParametroGlobalController {

    private final IParametroGlobalService parametroService;

    @Operation(
        summary = "Actualizar parámetro global",
        description = "Actualiza el parámetro global de tarifas. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Parámetro actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @PutMapping()
    public ResponseEntity<Void> actualizarParametroGlobal(
        @RequestBody ParametroRequestDto parametroRequestDto) {

        parametroService.actualizar(parametroRequestDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener parámetro global",
        description = "Devuelve el parámetro global de tarifas. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Parámetro obtenido"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping()
    public ResponseEntity<ParametroResponseDto> obtenerParametro() {
        return ResponseEntity.ok(parametroService.obtenerParametroGlobal());
    }
}
