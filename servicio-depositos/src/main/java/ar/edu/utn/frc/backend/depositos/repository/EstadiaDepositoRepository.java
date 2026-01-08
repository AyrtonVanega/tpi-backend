package ar.edu.utn.frc.backend.depositos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.depositos.model.EstadiaDeposito;
import ar.edu.utn.frc.backend.depositos.model.EstadiaDepositoId;

@Repository
public interface EstadiaDepositoRepository extends JpaRepository<EstadiaDeposito, EstadiaDepositoId> {
    
    @Query("SELECT e FROM EstadiaDeposito e " +
           "WHERE e.idEstadiaDeposito.idDeposito = :idDeposito " +
           "AND e.estado.codigo = :codigoEstado")
    List<EstadiaDeposito> buscarPorDepositoYEstado(
            @Param("idDeposito") Long idDeposito, 
            @Param("codigoEstado") String codigoEstado
    );

    @Query("SELECT e FROM EstadiaDeposito e " +
           "WHERE e.idEstadiaDeposito.idSolicitud = :idSolicitud")
    List<EstadiaDeposito> findByIdSolicitud(@Param("idSolicitud") Long idSolicitud);
}
