package ar.edu.utn.frc.backend.ServicioDepositos.service.impl;

import java.util.List;

import ar.edu.utn.frc.backend.ServicioDepositos.model.EstadoEstadiaDeposito;
import ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces.IEstadoEstadiaDepositoService;

public class EstadoEstadiaDepositoServiceImpl implements IEstadoEstadiaDepositoService {

    public EstadoEstadiaDeposito crear(EstadoEstadiaDeposito estadoEstadiaDeposito) {
        return estadoEstadiaDeposito;
    }

    public EstadoEstadiaDeposito actualizar(Long idEstadoEstadiaDeposito, EstadoEstadiaDeposito estadoEstadiaDeposito) {
        return estadoEstadiaDeposito;
    }

    public void eliminar(Long idEstadoEstadiaDeposito) {}

    public EstadoEstadiaDeposito obtenerPorId(Long idEstadoEstadiaDeposito) {
        return EstadoEstadiaDeposito.builder().build();
    }

    public List<EstadoEstadiaDeposito> obtenerTodos() {
        return List.of();
    }
}
