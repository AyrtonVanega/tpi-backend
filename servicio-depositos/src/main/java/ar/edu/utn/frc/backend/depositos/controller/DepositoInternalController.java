package ar.edu.utn.frc.backend.depositos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.depositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IDepositoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/depositos/internal")
public class DepositoInternalController {

    private final IDepositoService depositoService;

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @GetMapping("/{minLat}/{maxLat}/{minLon}/{maxLon}")
    public ResponseEntity<List<DepositoResponseDto>> obtenerDepositosEnBoundingBox(
            @PathVariable double minLat,
            @PathVariable double maxLat,
            @PathVariable double minLon,
            @PathVariable double maxLon) {

        return ResponseEntity.ok(depositoService.obtenerDepositosEnBoundingBox(minLat, maxLat, minLon, maxLon));
    }
}
