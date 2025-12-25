package ar.edu.utn.frc.backend.personas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.personas.model.Camion;

@Repository
public interface CamionRepository extends JpaRepository<Camion, String> {
    
    List<Camion> findByDisponibilidadTrue();
}
