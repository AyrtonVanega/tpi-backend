package ar.edu.utn.frc.backend.personas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.personas.dto.PutTransportistaDto;
import ar.edu.utn.frc.backend.personas.dto.TransportistaRequestDto;
import ar.edu.utn.frc.backend.personas.dto.TransportistaResponseDto;
import ar.edu.utn.frc.backend.personas.service.interfaces.ITransportistaService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/transportistas")
public class TransportistaController {
    
    private final ITransportistaService transportistaService;

    @PostMapping()
    public ResponseEntity<Void> registrarTransportista(@RequestBody TransportistaRequestDto transportistaRequestDto) {
        transportistaService.crear(transportistaRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{docTransportista}/{tipoDocTransportista}")
    public ResponseEntity<Void> actualizarTransportista(
            @PathVariable String docTransportista,
            @PathVariable String tipoDocTransportista,
            @RequestBody PutTransportistaDto transportistaRequestDto) {
        transportistaService.actualizar(docTransportista, tipoDocTransportista, transportistaRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{docTransportista}/{tipoDocTransportista}")
    public ResponseEntity<Void> darDeBajaTransportista(
            @PathVariable String docTransportista,
            @PathVariable String tipoDocTransportista) {
        transportistaService.eliminar(docTransportista, tipoDocTransportista);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{docTransportista}/{tipoDocTransportista}")
    public ResponseEntity<TransportistaResponseDto> obtenerTransportistaPorDoc(
            @PathVariable String docTransportista,
            @PathVariable String tipoDocTransportista) {
        return ResponseEntity.ok(transportistaService.obtenerPorId(docTransportista, tipoDocTransportista));
    }

    @GetMapping()
    public ResponseEntity<List<TransportistaResponseDto>> obtenerTransportistas() {
        return ResponseEntity.ok(transportistaService.obtenerTodos());
    }
}
