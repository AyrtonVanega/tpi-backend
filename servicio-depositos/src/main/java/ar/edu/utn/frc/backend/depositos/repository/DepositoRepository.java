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
    @Query(value = """
                SELECT d.*
                FROM depositos d
                JOIN ubicaciones u ON d.id_ubicacion = u.id_ubicacion
                WHERE ST_Within(
                    u.coordenadas,
                    ST_MakeEnvelope(:minLon, :minLat, :maxLon, :maxLat, 4326)
                )
            """, nativeQuery = true)
    List<Deposito> findDepositosEnBoundingBox(
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLon") double minLon,
            @Param("maxLon") double maxLon);

    boolean existsByUbicacionId(Long idUbicacion);
}
