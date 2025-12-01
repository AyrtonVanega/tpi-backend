package ar.edu.utn.frc.backend.ServicioDepositos.service.impl;

import java.util.List;

import ar.edu.utn.frc.backend.ServicioDepositos.model.EstadiaDeposito;
import ar.edu.utn.frc.backend.ServicioDepositos.model.EstadiaDepositoId;
import ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces.IEstadiaDepositoService;

public class EstadiaDepositoServiceImpl implements IEstadiaDepositoService {

    public EstadiaDeposito crear(EstadiaDeposito estadiaDeposito) {
        return estadiaDeposito;
    }

    public EstadiaDeposito actualizar(EstadiaDepositoId idEstadiaDeposito, EstadiaDeposito estadiaDeposito) {
        return estadiaDeposito;
    }

    public void eliminar(EstadiaDepositoId idEstadiaDeposito) {}

    public EstadiaDeposito obtenerPorId(EstadiaDepositoId idEstadiaDeposito) {
        return new EstadiaDeposito();
    }

    public List<EstadiaDeposito> obtenerTodos() {
        return List.of();
    }
}
