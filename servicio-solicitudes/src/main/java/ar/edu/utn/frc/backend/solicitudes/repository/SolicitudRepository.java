package ar.edu.utn.frc.backend.solicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.solicitudes.model.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
}
