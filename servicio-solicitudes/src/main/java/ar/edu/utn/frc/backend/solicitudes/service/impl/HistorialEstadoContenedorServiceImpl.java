package ar.edu.utn.frc.backend.solicitudes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;
import ar.edu.utn.frc.backend.solicitudes.model.EstadoContenedor;
import ar.edu.utn.frc.backend.solicitudes.model.HistorialEstadoContenedor;
import ar.edu.utn.frc.backend.solicitudes.repository.HistorialEstadoContenedorRepository;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IEstadoContenedorService;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IHistorialEstadoContenedorService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistorialEstadoContenedorServiceImpl implements IHistorialEstadoContenedorService {

    private final HistorialEstadoContenedorRepository historialRepository;

    private final IEstadoContenedorService estadoContenedorService;

    @Override
    public void registarCambioEstado(Contenedor contenedor, String codigoEstadoNuevo) {

        EstadoContenedor estadoContenedor = estadoContenedorService
                .buscarPorCodigo(codigoEstadoNuevo);

        HistorialEstadoContenedor historial = HistorialEstadoContenedor.builder()
                .fechaHora(LocalDateTime.now())
                .contenedor(contenedor)
                .estadoContenedor(estadoContenedor)
                .build();

        historialRepository.save(historial);
    }

    @Override
    public EstadoContenedor obtenerEstadoActual(Long idContenedor) {
        return historialRepository.findTopByContenedorIdContenedorOrderByFechaHoraDesc(idContenedor)
                .map(HistorialEstadoContenedor::getEstadoContenedor)
                .orElseThrow(() -> {
                    log.error("No hay historial para el contenedor {}", idContenedor);
                    return new RuntimeException();
                });
    }
}
