package ar.edu.utn.frc.backend.depositos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.depositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IUbicacionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ubicaciones/internal")
public class UbicacionInternalController {

    private final IUbicacionService ubicacionService;

    @PreAuthorize("hasRole('INTERNAL_CALL')")
    @PostMapping()
    public ResponseEntity<UbicacionResponseDto> crear(@RequestBody UbicacionRequestDto dto) {
        UbicacionResponseDto ubicacion = ubicacionService.crearSiNoExiste(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ubicacion);
    }
}
