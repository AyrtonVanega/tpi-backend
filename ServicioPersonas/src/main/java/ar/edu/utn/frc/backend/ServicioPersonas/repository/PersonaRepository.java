package ar.edu.utn.frc.backend.ServicioPersonas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.ServicioPersonas.model.Persona;
import ar.edu.utn.frc.backend.ServicioPersonas.model.PersonaId;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, PersonaId> {
    
}
