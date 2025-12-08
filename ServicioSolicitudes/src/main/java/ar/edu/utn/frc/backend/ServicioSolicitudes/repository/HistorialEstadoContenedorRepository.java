package ar.edu.utn.frc.backend.ServicioSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.ServicioSolicitudes.model.HistorialEstadoContenedor;

@Repository
public interface HistorialEstadoContenedorRepository extends JpaRepository<HistorialEstadoContenedor, Long> {
}
