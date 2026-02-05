package ar.edu.utn.frc.backend.personas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Hidden;

import ar.edu.utn.frc.backend.personas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.personas.service.interfaces.ICamionService;
import lombok.AllArgsConstructor;

@Hidden
@RestController
@AllArgsConstructor
@RequestMapping("/camiones/internal")
public class CamionInternalController {

    private final ICamionService camionService;

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @PatchMapping("/{patenteCamion}/reservar")
    public ResponseEntity<Void> reservarCamion(@PathVariable String patenteCamion) {
        camionService.reservarCamion(patenteCamion);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @PatchMapping("/{patenteCamion}/finalizar-recorrido")
    public ResponseEntity<CamionResponseDto> finalizarRecorrido(@PathVariable String patenteCamion) {
        return ResponseEntity.ok(camionService.finalizarRecorrido(patenteCamion));
    }

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @GetMapping("/consumo-promedio")
    public ResponseEntity<Double> calcularConsumoPromedio(
            @RequestParam double pesoMin,
            @RequestParam double pesoMax,
            @RequestParam double volMin,
            @RequestParam double volMax) {

        return ResponseEntity.ok(camionService.calcularConsumoPromedio(pesoMin, pesoMax, volMin, volMax));
    }
}
