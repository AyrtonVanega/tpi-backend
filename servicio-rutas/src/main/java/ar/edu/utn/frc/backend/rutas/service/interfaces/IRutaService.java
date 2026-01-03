package ar.edu.utn.frc.backend.rutas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.rutas.dto.RutaTentativaDto;

public interface IRutaService {

    List<RutaTentativaDto> obtenerRutasTentativas(Long idOrigen, double latitudOrigen, double longitudOrigen,
            Long idDestino, double latitudDestino, double longitudDestino);
}
