package ar.edu.utn.frc.backend.solicitudes.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.backend.solicitudes.dto.EstadoSolicitudRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.EstadoSolicitudResponseDto;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IEstadoSolicitudService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/estados-solicitud")
public class EstadoSolicitudController {

    private final IEstadoSolicitudService estadoSolicitudService;

    @PostMapping
    public ResponseEntity<EstadoSolicitudResponseDto> crearEstadoSolicitud(
            @RequestBody EstadoSolicitudRequestDto estadoSolicitudRequestDto) {

        EstadoSolicitudResponseDto estadoSolicitudCreado = estadoSolicitudService.crear(estadoSolicitudRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estadoSolicitudCreado);
    }

    @PutMapping("/{idEstadoSolicitud}")
    public ResponseEntity<EstadoSolicitudResponseDto> actualizarEstadoSolicitud(
            @PathVariable Long idEstadoSolicitud,
            @RequestBody EstadoSolicitudRequestDto estadoSolicitudRequestDto) {

        return ResponseEntity.ok(estadoSolicitudService.actualizar(idEstadoSolicitud, estadoSolicitudRequestDto));
    }

    @DeleteMapping("/{idEstadoSolicitud}")
    public ResponseEntity<Void> eliminarEstadoSolicitud(@PathVariable Long idEstadoSolicitud) {
        estadoSolicitudService.eliminar(idEstadoSolicitud);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idEstadoSolicitud}")
    public ResponseEntity<EstadoSolicitudResponseDto> obtenerEstadoSolicitud(@PathVariable Long idEstadoSolicitud) {
        return ResponseEntity.ok(estadoSolicitudService.obtenerPorId(idEstadoSolicitud));
    }

    @GetMapping()
    public ResponseEntity<List<EstadoSolicitudResponseDto>> obtenerEstadosSolicitud() {
        return ResponseEntity.ok(estadoSolicitudService.obtenerTodos());
    }
}
