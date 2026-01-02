package ar.edu.utn.frc.backend.rutas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.rutas.dto.RutaTentativaDto;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IRutaService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/rutas")
public class RutaController {

    private IRutaService rutaService;

    @GetMapping("/{latitudOrigen}/{longitudOrigen}/{latitudDestino}/{longitudDestino}")
    public ResponseEntity<List<RutaTentativaDto>> obtenerRutasTentativas(
            @PathVariable double latitudOrigen,
            @PathVariable double longitudOrigen,
            @PathVariable double latitudDestino,
            @PathVariable double longitudDestino) {

        return ResponseEntity.ok(rutaService.obtenerRutasTentativas(latitudOrigen, longitudOrigen, latitudDestino, longitudDestino));
    }
}
