package ar.edu.utn.frc.backend.personas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.personas.model.Camion;

@Repository
public interface CamionRepository extends JpaRepository<Camion, String> {

        List<Camion> findByDisponibilidadTrue();

        @Query("SELECT c FROM Camion c " +
                        "WHERE (c.peso >= :pesoMin AND c.peso < :pesoMax) " +
                        "AND (c.volumen >= :volMin AND c.volumen < :volMax)")
        List<Camion> buscarCamionesPorCapacidad(
                        @Param("pesoMin") double pesoMin,
                        @Param("pesoMax") double pesoMax,
                        @Param("volMin") double volMin,
                        @Param("volMax") double volMax);

        @Query("SELECT c FROM Camion c " +
                        "WHERE c.transportista.idPersona.doc = :doc " +
                        "AND c.transportista.idPersona.tipoDoc = :tipoDoc")
        Optional<Camion> buscarCamionPorTransportista(
                        @Param("doc") String doc,
                        @Param("tipoDoc") String tipoDoc);
}
