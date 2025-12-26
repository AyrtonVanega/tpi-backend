package ar.edu.utn.frc.backend.rutas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.rutas.model.Tramo;
import ar.edu.utn.frc.backend.rutas.model.TramoId;

@Repository
public interface TramoRepository extends JpaRepository<Tramo, TramoId> {
    
}
