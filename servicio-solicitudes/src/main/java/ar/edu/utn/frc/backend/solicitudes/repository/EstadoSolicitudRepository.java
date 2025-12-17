package ar.edu.utn.frc.backend.solicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.solicitudes.model.EstadoSolicitud;

@Repository
public interface EstadoSolicitudRepository extends JpaRepository<EstadoSolicitud, Long> {
}
