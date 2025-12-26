package ar.edu.utn.frc.backend.rutas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.rutas.model.EstadoTramo;

@Repository
public interface EstadoTramoRepository extends JpaRepository<EstadoTramo, Long> {
    
    Optional<EstadoTramo> findByCodigo(String codigo);
}
