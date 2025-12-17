package ar.edu.utn.frc.backend.depositos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.depositos.model.Deposito;

@Repository
public interface DepositoRepository extends JpaRepository<Deposito, Long> {
    
}
