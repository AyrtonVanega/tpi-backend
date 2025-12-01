package ar.edu.utn.frc.backend.ServicioDepositos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.backend.ServicioDepositos.model.EstadoEstadiaDeposito;

@Repository
public interface EstadoEstadiaDepositoRepository extends JpaRepository<EstadoEstadiaDeposito, Long> {
    
}
