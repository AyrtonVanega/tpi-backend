package ar.edu.utn.frc.backend.tarifas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.tarifas.dto.ParametroResponseDto;
import ar.edu.utn.frc.backend.tarifas.service.interfaces.IParametroGlobalService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;

@Hidden
@RestController
@AllArgsConstructor
@RequestMapping("/parametro-global/internal")
public class ParametroGlobalInternalController {

    private final IParametroGlobalService parametroService;

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @GetMapping()
    public ResponseEntity<ParametroResponseDto> obtenerParametro() {
        return ResponseEntity.ok(parametroService.obtenerParametroGlobal());
    }
}
