package ar.edu.utn.frc.backend.depositos.service.interfaces;

import ar.edu.utn.frc.backend.depositos.model.EstadoEstadiaDeposito;

public interface IEstadoEstadiaDepositoService {
    
    EstadoEstadiaDeposito buscarPorCodigo(String codigo);
}
