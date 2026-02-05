package ar.edu.utn.frc.backend.rutas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.rutas.dto.PatchTramoDto;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITramoService;
import ar.edu.utn.frc.backend.rutas.workflow.FinalizarTramoWorkflow;
import ar.edu.utn.frc.backend.rutas.workflow.IniciarTramoWorkflow;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/tramos")
public class TramoController {

    private final ITramoService tramoService;
    private final IniciarTramoWorkflow iniciarTramoWorkflow;
    private final FinalizarTramoWorkflow finalizarTramoWorkflow;

    @PreAuthorize("hasRole('OPERADOR')")
    @PatchMapping("/{idRuta}/{orden}/asignar-camion")
    public ResponseEntity<Void> asignarCamionATramo(@PathVariable Long idRuta, @PathVariable int orden,
            @RequestBody PatchTramoDto dto) {

        tramoService.asignarCamion(idRuta, orden, dto);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('TRANSPORTISTA')")
    @PatchMapping("/{idRuta}/{orden}/iniciar")
    public ResponseEntity<Void> iniciarTramo(@PathVariable Long idRuta, @PathVariable int orden) {
        iniciarTramoWorkflow.iniciarTramo(idRuta, orden);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('TRANSPORTISTA')")
    @PatchMapping("/{idRuta}/{orden}/finalizar")
    public ResponseEntity<Void> finalizarTramo(@PathVariable Long idRuta, @PathVariable int orden) {
        finalizarTramoWorkflow.finalizarTramo(idRuta, orden);
        return ResponseEntity.noContent().build();
    }
}
