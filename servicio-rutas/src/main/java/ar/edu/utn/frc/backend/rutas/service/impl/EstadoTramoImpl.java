package ar.edu.utn.frc.backend.rutas.service.impl;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.exception.ResourceNotFoundException;
import ar.edu.utn.frc.backend.rutas.model.EstadoTramo;
import ar.edu.utn.frc.backend.rutas.repository.EstadoTramoRepository;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IEstadoTramoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstadoTramoImpl implements IEstadoTramoService {

    private final EstadoTramoRepository estadoTramoRepository;

    @Override
    public EstadoTramo buscarPorCodigo(String codigo) {
        return estadoTramoRepository.findByCodigo(codigo)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("EstadoTramo con codigo " + codigo + " no encontrado");
                });
    }
}
