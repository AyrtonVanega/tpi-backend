package ar.edu.utn.frc.backend.depositos.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.depositos.exception.ResourceNotFoundException;
import ar.edu.utn.frc.backend.depositos.model.EstadoEstadiaDeposito;
import ar.edu.utn.frc.backend.depositos.repository.EstadoEstadiaDepositoRepository;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IEstadoEstadiaDepositoService;

@Service
@RequiredArgsConstructor
public class EstadoEstadiaDepositoServiceImpl implements IEstadoEstadiaDepositoService {

    private final EstadoEstadiaDepositoRepository estadoEstadiaDepositoRepository;

    @Override
    public EstadoEstadiaDeposito buscarPorCodigo(String codigo) {
        return estadoEstadiaDepositoRepository.findByCodigo(codigo)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Estado " + codigo + " no encontrado");
                });
    }
}
