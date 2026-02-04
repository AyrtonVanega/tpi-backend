package ar.edu.utn.frc.backend.personas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.personas.client.dto.TramoResponseDto;
import ar.edu.utn.frc.backend.personas.dto.CreateTransportistaDto;
import ar.edu.utn.frc.backend.personas.dto.PutTransportistaDto;
import ar.edu.utn.frc.backend.personas.dto.TransportistaResponseDto;
import ar.edu.utn.frc.backend.personas.service.interfaces.ITransportistaService;
import ar.edu.utn.frc.backend.personas.workflow.ConsultarTramosAsignadosWorkflow;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/transportistas")
public class TransportistaController {

    private final ITransportistaService transportistaService;
    private final ConsultarTramosAsignadosWorkflow consultarTramosAsignadosWorkflow;

    @PreAuthorize("hasRole('OPERADOR')")
    @PostMapping()
    public ResponseEntity<Void> registrarTransportista(@RequestBody CreateTransportistaDto transportistaRequestDto) {
        transportistaService.crear(transportistaRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @PutMapping("/{docTransportista}/{tipoDocTransportista}")
    public ResponseEntity<Void> actualizarTransportista(
            @PathVariable String docTransportista,
            @PathVariable String tipoDocTransportista,
            @RequestBody PutTransportistaDto transportistaRequestDto) {
        transportistaService.actualizar(docTransportista, tipoDocTransportista, transportistaRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @DeleteMapping("/{docTransportista}/{tipoDocTransportista}")
    public ResponseEntity<Void> darDeBajaTransportista(
            @PathVariable String docTransportista,
            @PathVariable String tipoDocTransportista) {
        transportistaService.eliminar(docTransportista, tipoDocTransportista);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping("/{docTransportista}/{tipoDocTransportista}")
    public ResponseEntity<TransportistaResponseDto> obtenerTransportistaPorDoc(
            @PathVariable String docTransportista,
            @PathVariable String tipoDocTransportista) {
        return ResponseEntity.ok(transportistaService.obtenerPorId(docTransportista, tipoDocTransportista));
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @GetMapping()
    public ResponseEntity<List<TransportistaResponseDto>> obtenerTransportistas() {
        return ResponseEntity.ok(transportistaService.obtenerTodos());
    }

    @PreAuthorize("hasRole('TRANSPORTISTA')")
    @GetMapping("/{docTransportista}/{tipoDocTransportista}/tramos-asignados")
    public ResponseEntity<List<TramoResponseDto>> obtenerTramosAsignados(
            @PathVariable String docTransportista,
            @PathVariable String tipoDocTransportista) {

        return ResponseEntity
                .ok(consultarTramosAsignadosWorkflow.consultarTramosAsignados(docTransportista, tipoDocTransportista));
    }
}
