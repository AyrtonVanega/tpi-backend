package ar.edu.utn.frc.backend.solicitudes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.solicitudes.exception.ResourceNotFoundException;
import ar.edu.utn.frc.backend.solicitudes.model.EstadoContenedor;
import ar.edu.utn.frc.backend.solicitudes.repository.EstadoContenedorRepository;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IEstadoContenedorService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstadoContenedorServiceImpl implements IEstadoContenedorService {

    private final EstadoContenedorRepository estadoContenedorRepository;

    @Override
    public EstadoContenedor buscarPorCodigo(String codigo) {
        return estadoContenedorRepository.findByCodigo(codigo)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("EstadoContenedor con codigo " + codigo + " no encontrado");
                });
    }
}
