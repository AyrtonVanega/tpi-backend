package ar.edu.utn.frc.backend.tarifas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.tarifas.model.CamionView;

@Repository
public interface CamionViewRepository extends JpaRepository<CamionView, String> {

        @Query("SELECT AVG(c.consumoCombustiblePromedio) FROM CamionView c " +
                        "WHERE c.peso >= :pesoMaxTarifa " +
                        "AND c.volumen >= :volumenMaxTarifa")
        Double obtenerPromedioConsumoDeCamionesAptos(
                        @Param("pesoMaxTarifa") double pesoMax,
                        @Param("volumenMaxTarifa") double volumenMax);
}
