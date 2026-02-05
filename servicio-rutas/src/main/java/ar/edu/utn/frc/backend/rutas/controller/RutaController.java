package ar.edu.utn.frc.backend.rutas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

import ar.edu.utn.frc.backend.rutas.dto.CostoRutaResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.CreateRutaDto;
import ar.edu.utn.frc.backend.rutas.dto.FiltroRutaDto;
import ar.edu.utn.frc.backend.rutas.dto.RutaResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.RutaTentativaDto;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IRutaService;
import lombok.AllArgsConstructor;

@Tag(
    name = "Rutas", 
    description = "Operaciones de administracion sobre Rutas"
)
@RestController
@AllArgsConstructor
@RequestMapping("/rutas")
public class RutaController {

    private final IRutaService rutaService;

    @Operation(
        summary = "Obtener rutas tentativas",
        description = "Devuelve rutas tentativas según filtros. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de rutas tentativas"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/tentativas")
    public ResponseEntity<List<RutaTentativaDto>> obtenerRutasTentativas(@ModelAttribute FiltroRutaDto filtro) {

        List<RutaTentativaDto> rutasTentativas = rutaService.obtenerRutasTentativas(
                filtro.getIdUbicacionOrigen(),
                filtro.getLatitudOrigen(),
                filtro.getLongitudOrigen(),
                filtro.getIdUbicacionDestino(),
                filtro.getLatitudDestino(),
                filtro.getLongitudDestino(),
                filtro.getCostoKmBase(),
                filtro.getConsumoCombustibleAprox());

        return ResponseEntity.ok(rutasTentativas);
    }

    @Operation(
        summary = "Crear una ruta",
        description = "Registra una nueva ruta en el sistema. Requiere rol OPERADOR.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ruta creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PreAuthorize("hasRole('OPERADOR')")
    @PostMapping()
    public ResponseEntity<Void> crearRuta(@RequestBody CreateRutaDto dto) {
        rutaService.crear(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(
        summary = "Obtener ruta por ID",
        description = "Devuelve la información de una ruta específica. Requiere rol OPERADOR o CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ruta encontrada"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Ruta no encontrada")
    })
    @PreAuthorize("hasAnyRole('OPERADOR','CLIENTE')")
    @GetMapping("/{idRuta}")
    public ResponseEntity<RutaResponseDto> obtenerRutaPorId(@PathVariable Long idRuta) {
        return ResponseEntity.ok(rutaService.obtenerPorId(idRuta));
    }

    @Operation(
        summary = "Mostrar costos de ruta",
        description = "Devuelve los costos asociados a una ruta. Requiere rol CLIENTE.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Costos calculados"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Ruta no encontrada")
    })
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{idRuta}/costos")
    public ResponseEntity<CostoRutaResponseDto> mostrarCostos(@PathVariable Long idRuta) {
        return ResponseEntity.ok(rutaService.mostrarCostos(idRuta));
    }
}
