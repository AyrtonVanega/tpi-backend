package ar.edu.utn.frc.backend.tarifas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.tarifas.model.Tarifa;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    /*
     * Dos rangos [Min_1, Max_1] y [Min_2, Max_2] se solapan SI Y SOLO SI:
     * Min_1 < Max_2 AND Min_2 < Max_1
     */
    @Query("SELECT COUNT(t) > 0 FROM Tarifa t " +
            "WHERE (t.rangoPesoMin < :nuevoMaxPeso AND :nuevoMinPeso < t.rangoPesoMax) " +
            "AND (t.rangoVolumenMin < :nuevoMaxVol AND :nuevoMinVol < t.rangoVolumenMax)")
    boolean existeSolapamiento(
            @Param("nuevoMinPeso") double nuevoMinPeso,
            @Param("nuevoMaxPeso") double nuevoMaxPeso,
            @Param("nuevoMinVol") double nuevoMinVol,
            @Param("nuevoMaxVol") double nuevoMaxVol);
}
