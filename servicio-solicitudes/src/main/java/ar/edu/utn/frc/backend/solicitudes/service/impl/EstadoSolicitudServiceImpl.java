package ar.edu.utn.frc.backend.solicitudes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.solicitudes.exception.ResourceNotFoundException;
import ar.edu.utn.frc.backend.solicitudes.model.EstadoSolicitud;
import ar.edu.utn.frc.backend.solicitudes.repository.EstadoSolicitudRepository;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IEstadoSolicitudService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstadoSolicitudServiceImpl implements IEstadoSolicitudService {

    private final EstadoSolicitudRepository estadoSolicitudRepository;

    @Override
    public EstadoSolicitud buscarPorCodigo(String codigo) {
        return estadoSolicitudRepository.findByCodigo(codigo)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("EstadoSolicitud con codigo " + codigo + " no encontrado");
                });
    }
}
