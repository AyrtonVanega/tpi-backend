package ar.edu.utn.frc.backend.tarifas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.tarifas.model.ParametroGlobal;

@Repository
public interface ParametroGlobalRepository extends JpaRepository<ParametroGlobal, Long> {
    
}
