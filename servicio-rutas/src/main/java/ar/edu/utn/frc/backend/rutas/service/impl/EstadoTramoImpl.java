package ar.edu.utn.frc.backend.rutas.service.impl;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.model.EstadoTramo;
import ar.edu.utn.frc.backend.rutas.repository.EstadoTramoRepository;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IEstadoTramoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstadoTramoImpl implements IEstadoTramoService {

    private final EstadoTramoRepository estadoTramoRepository;

    @Override
    public EstadoTramo buscarPorCodigo(String codigo) {
        return estadoTramoRepository.findByCodigo(codigo)
                .orElseThrow(() -> {
                    log.error("Estado {} no encontrado", codigo);
                    return new RuntimeException();
                });
    }
}
