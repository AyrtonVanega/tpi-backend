package ar.edu.utn.frc.backend.solicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;

@Repository
public interface ContenedorRepository extends JpaRepository<Contenedor, Long> {

    @Query("SELECT c FROM Contenedor c " +
           "JOIN c.historialesEstadoContenedor h " +
           "WHERE h.fechaHora = (SELECT MAX(h2.fechaHora) FROM HistorialEstadoContenedor h2 WHERE h2.contenedor = c) " +
           "AND h.estadoContenedor.codigo <> :codigoEstado")
    List<Contenedor> buscarPorEstadoActualQueNoSea(@Param("codigoEstado") String codigoEstado);
}
