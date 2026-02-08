package ar.edu.utn.frc.backend.tarifas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.tarifas.model.CamionView;

@Repository
public interface CamionViewRepository extends JpaRepository<CamionView, String> {

    @Query("SELECT c FROM CamionView c " +
            "WHERE (c.peso >= :pesoMin AND c.peso < :pesoMax) " +
            "AND (c.volumen >= :volMin AND c.volumen < :volMax)")
    List<CamionView> buscarCamionesPorCapacidad(
            @Param("pesoMin") double pesoMin,
            @Param("pesoMax") double pesoMax,
            @Param("volMin") double volMin,
            @Param("volMax") double volMax);
}
