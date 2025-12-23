package ar.edu.utn.frc.backend.depositos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.depositos.model.EstadoEstadiaDeposito;

@Repository
public interface EstadoEstadiaDepositoRepository extends JpaRepository<EstadoEstadiaDeposito, Long> {
    
    Optional<EstadoEstadiaDeposito> findByCodigo(String codigo);
}
