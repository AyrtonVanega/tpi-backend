package ar.edu.utn.frc.backend.depositos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.depositos.model.Deposito;

@Repository
public interface DepositoRepository extends JpaRepository<Deposito, Long> {

    // Busca depósitos dentro de un rectangulo de latitudes y longuitdes
    @Query("SELECT d FROM Deposito d " +
            "WHERE d.latitud BETWEEN :minLat AND :maxLat " +
            "AND d.longitud BETWEEN :minLon AND :maxLon")
    List<Deposito> findDepositosEnBoundingBox(
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLon") double minLon,
            @Param("maxLon") double maxLon);
}
