package ar.edu.utn.frc.backend.depositos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.depositos.model.EstadiaDeposito;
import ar.edu.utn.frc.backend.depositos.model.EstadiaDepositoId;

@Repository
public interface EstadiaDepositoRepository extends JpaRepository<EstadiaDeposito, EstadiaDepositoId> {
    
}
