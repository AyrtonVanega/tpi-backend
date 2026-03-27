package ar.edu.utn.frc.backend.depositos.repository;

import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.depositos.model.Ubicacion;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long> {

    @Query(value = """
                SELECT *
                FROM ubicaciones u
                WHERE ST_DWithin(u.coordenadas, :punto, :distancia)
            """, nativeQuery = true)
    Optional<Ubicacion> buscarPorCoordenadasAprox(
            @Param("punto") Point punto,
            @Param("distancia") double distancia);
}
