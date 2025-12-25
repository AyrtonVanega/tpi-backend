package ar.edu.utn.frc.backend.personas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.personas.model.PersonaId;
import ar.edu.utn.frc.backend.personas.model.Transportista;

@Repository
public interface TransportistaRepository extends JpaRepository<Transportista, PersonaId> {
    
}
