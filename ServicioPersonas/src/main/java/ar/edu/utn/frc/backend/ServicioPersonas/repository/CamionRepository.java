package ar.edu.utn.frc.backend.ServicioPersonas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.ServicioPersonas.model.Camion;

@Repository
public interface CamionRepository extends JpaRepository<Camion, String> {
    
}
