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

import ar.edu.utn.frc.backend.rutas.dto.CostoRutaResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.CreateRutaDto;
import ar.edu.utn.frc.backend.rutas.dto.FiltroRutaDto;
import ar.edu.utn.frc.backend.rutas.dto.RutaResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.RutaTentativaDto;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IRutaService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/rutas")
public class RutaController {

    private final IRutaService rutaService;

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

    @PreAuthorize("hasRole('OPERADOR')")
    @PostMapping()
    public ResponseEntity<Void> crearRuta(@RequestBody CreateRutaDto dto) {
        rutaService.crear(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PreAuthorize("hasAnyRole('OPERADOR','CLIENTE')")
    @GetMapping("/{idRuta}")
    public ResponseEntity<RutaResponseDto> obtenerRutaPorId(@PathVariable Long idRuta) {
        return ResponseEntity.ok(rutaService.obtenerPorId(idRuta));
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/{idRuta}/costos")
    public ResponseEntity<CostoRutaResponseDto> mostrarCostos(@PathVariable Long idRuta) {
        return ResponseEntity.ok(rutaService.mostrarCostos(idRuta));
    }
}
