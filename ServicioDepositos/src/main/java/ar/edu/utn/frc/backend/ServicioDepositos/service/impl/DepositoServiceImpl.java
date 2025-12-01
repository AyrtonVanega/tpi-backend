package ar.edu.utn.frc.backend.ServicioDepositos.service.impl;

import java.util.List;

import ar.edu.utn.frc.backend.ServicioDepositos.model.Deposito;
import ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces.IDepositoService;

public class DepositoServiceImpl implements IDepositoService {

    public Deposito crear(Deposito deposito) {
        return deposito;
    }

    public Deposito actualizar(Long idDeposito, Deposito deposito) {
        return deposito;
    }

    public void eliminar(Long idDeposito) {}

    public Deposito obtenerPorId(Long idDeposito) {
        return Deposito.builder().build();
    }

    public List<Deposito> obtenerTodos() {
        return List.of();
    }
}
