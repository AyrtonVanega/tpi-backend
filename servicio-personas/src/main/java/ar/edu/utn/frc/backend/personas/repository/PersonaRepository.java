package ar.edu.utn.frc.backend.personas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.personas.model.Persona;
import ar.edu.utn.frc.backend.personas.model.PersonaId;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, PersonaId> {
    
}
