package ar.edu.utn.frc.backend.solicitudes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.solicitudes.dto.PatchContenedorDto;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IContenedorService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/contenedores/internal")
public class ContenedorInternalController {

    private final IContenedorService contenedorService;

    @PatchMapping("/{idContenedor}/estado")
    public ResponseEntity<Void> actualizarEstadoContenedor(
            @PathVariable Long idContenedor,
            @RequestBody PatchContenedorDto contenedorRequestDto) {

        contenedorService.actualizarEstado(idContenedor, contenedorRequestDto);
        return ResponseEntity.noContent().build();
    }
}
