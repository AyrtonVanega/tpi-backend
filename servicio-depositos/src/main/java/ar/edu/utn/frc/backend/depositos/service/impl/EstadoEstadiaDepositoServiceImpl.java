package ar.edu.utn.frc.backend.depositos.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.depositos.model.EstadoEstadiaDeposito;
import ar.edu.utn.frc.backend.depositos.repository.EstadoEstadiaDepositoRepository;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IEstadoEstadiaDepositoService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstadoEstadiaDepositoServiceImpl implements IEstadoEstadiaDepositoService {

    private final EstadoEstadiaDepositoRepository estadoEstadiaDepositoRepository;

    @Override
    public EstadoEstadiaDeposito buscarPorCodigo(String codigo) {
        return estadoEstadiaDepositoRepository.findByCodigo(codigo)
                .orElseThrow(() -> {
                    log.error("Estado {} no encontrado", codigo);
                    return new RuntimeException();
                });
    }
}
