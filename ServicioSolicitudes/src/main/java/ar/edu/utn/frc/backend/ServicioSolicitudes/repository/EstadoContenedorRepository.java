package ar.edu.utn.frc.backend.ServicioSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.ServicioSolicitudes.model.EstadoContenedor;

@Repository
public interface EstadoContenedorRepository extends JpaRepository<EstadoContenedor, Long> {
}
