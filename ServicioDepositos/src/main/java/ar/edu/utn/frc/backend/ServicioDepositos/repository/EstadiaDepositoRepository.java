package ar.edu.utn.frc.backend.ServicioDepositos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.ServicioDepositos.model.EstadiaDeposito;
import ar.edu.utn.frc.backend.ServicioDepositos.model.EstadiaDepositoId;

@Repository
public interface EstadiaDepositoRepository extends JpaRepository<EstadiaDeposito, EstadiaDepositoId> {
    
}
