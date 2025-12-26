package ar.edu.utn.frc.backend.rutas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.rutas.model.Ruta;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    
}
