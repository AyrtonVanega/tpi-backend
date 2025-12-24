package ar.edu.utn.frc.backend.solicitudes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.solicitudes.model.EstadoContenedor;

@Repository
public interface EstadoContenedorRepository extends JpaRepository<EstadoContenedor, Long> {

    Optional<EstadoContenedor> findByCodigo(String codigo);
}
