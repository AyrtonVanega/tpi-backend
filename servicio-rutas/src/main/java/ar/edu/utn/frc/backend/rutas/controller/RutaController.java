package ar.edu.utn.frc.backend.rutas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.rutas.dto.FiltroRutaDto;
import ar.edu.utn.frc.backend.rutas.dto.RutaTentativaDto;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IRutaService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/rutas")
public class RutaController {

    private final IRutaService rutaService;

    @GetMapping("/tentativas")
    public ResponseEntity<List<RutaTentativaDto>> obtenerRutasTentativas(@ModelAttribute FiltroRutaDto filtro) {

        List<RutaTentativaDto> rutasTentativas = rutaService.obtenerRutasTentativas(
                filtro.getIdUbicacionOrigen(),
                filtro.getLatitudOrigen(),
                filtro.getLongitudOrigen(),
                filtro.getIdUbicacionDestino(),
                filtro.getLatitudDestino(),
                filtro.getLongitudDestino());

        return ResponseEntity.ok(rutasTentativas);
    }
}
