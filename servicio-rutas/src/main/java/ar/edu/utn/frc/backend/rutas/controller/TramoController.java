package ar.edu.utn.frc.backend.rutas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.backend.rutas.dto.PatchTramoDto;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITramoService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/tramos")
public class TramoController {

    private final ITramoService tramoService;

    @PatchMapping("/{idRuta}/{orden}/asignar-camion")
    public ResponseEntity<Void> asignarCamionATramo(@PathVariable Long idRuta, @PathVariable int orden,
            @RequestBody PatchTramoDto dto) {

        tramoService.asignarCamion(idRuta, orden, dto);
        return ResponseEntity.noContent().build();
    }
}
