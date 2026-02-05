package ar.edu.utn.frc.backend.rutas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.rutas.dto.TramoResponseDto;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITramoService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/tramos/internal")
public class TramoInternalController {

    private final ITramoService tramoService;

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @GetMapping("/patente-estado")
    public ResponseEntity<List<TramoResponseDto>> buscarTramosPorPatenteYEstado(
            @RequestParam String patenteCamion,
            @RequestParam String estado) {

        return ResponseEntity.ok(tramoService.obtenerTramosPorPatenteCamionYEstado(patenteCamion, estado));
    }
}
