package ar.edu.utn.frc.backend.depositos.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.depositos.dto.EstadoEstadiaDepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.EstadoEstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.mapper.EstadoEstadiaDepositoMapper;
import ar.edu.utn.frc.backend.depositos.model.EstadoEstadiaDeposito;
import ar.edu.utn.frc.backend.depositos.repository.EstadoEstadiaDepositoRepository;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IEstadoEstadiaDepositoService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstadoEstadiaDepositoServiceImpl implements IEstadoEstadiaDepositoService {

    private final EstadoEstadiaDepositoRepository estadoEstadiaDepositoRepository;

    private final EstadoEstadiaDepositoMapper estadoMapper;

    @Override
    public EstadoEstadiaDepositoResponseDto crear(EstadoEstadiaDepositoRequestDto dto) {
        EstadoEstadiaDeposito estado = estadoMapper.toEntity(dto);
        estadoEstadiaDepositoRepository.save(estado);
        return estadoMapper.toResponse(estado);
    }

    @Override
    public EstadoEstadiaDepositoResponseDto actualizar(
            Long idEstadoEstadiaDeposito, EstadoEstadiaDepositoRequestDto dto
    ) {
        EstadoEstadiaDeposito estado = estadoEstadiaDepositoRepository.findById(idEstadoEstadiaDeposito)
                .orElseThrow(() -> {
                    log.error("Estado {} no encontrado", idEstadoEstadiaDeposito);
                    return new RuntimeException();
                });

        estado.setCodigo(dto.getCodigo());
        estado.setDescripcion(dto.getDescripcion());

        estadoEstadiaDepositoRepository.save(estado);
        return estadoMapper.toResponse(estado);
    }

    @Override
    public void eliminar(Long idEstadoEstadiaDeposito) {
        EstadoEstadiaDeposito estado = estadoEstadiaDepositoRepository.findById(idEstadoEstadiaDeposito)
                .orElseThrow(() -> {
                    log.error("Estado {} no encontrado", idEstadoEstadiaDeposito);
                    return new RuntimeException();
                });
        estadoEstadiaDepositoRepository.delete(estado);
    }

    @Override
    public EstadoEstadiaDepositoResponseDto obtenerPorId(Long idEstadoEstadiaDeposito) {
        EstadoEstadiaDeposito estado = estadoEstadiaDepositoRepository.findById(idEstadoEstadiaDeposito)
                .orElseThrow(() -> {
                    log.error("Estado {} no encontrado", idEstadoEstadiaDeposito);
                    return new RuntimeException();
                });
        return estadoMapper.toResponse(estado);
    }

    @Override
    public List<EstadoEstadiaDepositoResponseDto> obtenerTodos() {
        List<EstadoEstadiaDeposito> estados = estadoEstadiaDepositoRepository.findAll();
        return estados.stream()
                .map(estadoMapper::toResponse)
                .toList();
    }
}
