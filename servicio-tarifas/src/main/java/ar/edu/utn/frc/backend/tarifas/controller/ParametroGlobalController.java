package ar.edu.utn.frc.backend.tarifas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.tarifas.dto.ParametroRequestDto;
import ar.edu.utn.frc.backend.tarifas.dto.ParametroResponseDto;
import ar.edu.utn.frc.backend.tarifas.service.interfaces.IParametroGlobalService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/parametro-global")
public class ParametroGlobalController {

    private final IParametroGlobalService parametroService;

    @PutMapping()
    public ResponseEntity<Void> actualizarParametroGlobal(
        @RequestBody ParametroRequestDto parametroRequestDto) {

        parametroService.actualizar(parametroRequestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<ParametroResponseDto> obtenerParametro() {
        return ResponseEntity.ok(parametroService.obtenerParametroGlobal());
    }
}
