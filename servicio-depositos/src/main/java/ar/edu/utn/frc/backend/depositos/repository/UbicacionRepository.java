package ar.edu.utn.frc.backend.depositos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.depositos.model.Ubicacion;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {
    
}
