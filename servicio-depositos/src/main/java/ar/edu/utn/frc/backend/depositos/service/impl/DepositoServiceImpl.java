package ar.edu.utn.frc.backend.depositos.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.depositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.mapper.DepositoMapper;
import ar.edu.utn.frc.backend.depositos.model.Deposito;
import ar.edu.utn.frc.backend.depositos.repository.DepositoRepository;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IDepositoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepositoServiceImpl implements IDepositoService {

    private final DepositoRepository depositoRepository;
    private final DepositoMapper depositoMapper;

    @Override
    public DepositoResponseDto crear(DepositoRequestDto depositoRequestDto) {
        Deposito deposito = depositoMapper.toEntity(depositoRequestDto);
        depositoRepository.save(deposito);
        return depositoMapper.toResponse(deposito);
    }

    @Override
    public DepositoResponseDto actualizar(Long idDeposito, DepositoRequestDto depositoRequestDto) {
        Deposito deposito = depositoRepository.findById(idDeposito)
                .orElseThrow(() -> {
                    log.error("Deposito {} no encontrado", idDeposito);
                    return new RuntimeException();
                });

        deposito.setDireccion(depositoRequestDto.getDireccion());
        deposito.setLatitud(depositoRequestDto.getLatitud());
        deposito.setLongitud(depositoRequestDto.getLongitud());
        deposito.setNombreCiudad(depositoRequestDto.getNombreCiudad());
        deposito.setCostoEstadiaDiaria(depositoRequestDto.getCostoEstadiaDiaria());

        depositoRepository.save(deposito);

        return depositoMapper.toResponse(deposito);
    }

    @Override
    public void eliminar(Long idDeposito) {
        Deposito deposito = depositoRepository.findById(idDeposito)
                .orElseThrow(() -> {
                    log.error("Deposito {} no encontrado", idDeposito);
                    return new RuntimeException();
                });

        depositoRepository.delete(deposito);
    }

    @Override
    public DepositoResponseDto obtenerPorId(Long idDeposito) {
        Deposito deposito = depositoRepository.findById(idDeposito)
                .orElseThrow(() -> {
                    log.error("Deposito {} no encontrado", idDeposito);
                    return new RuntimeException();
                });

        return depositoMapper.toResponse(deposito);
    }

    @Override
    public List<DepositoResponseDto> obtenerTodos() {
        List<Deposito> depositos = depositoRepository.findAll();
        return depositos.stream()
                .map(depositoMapper::toResponse)
                .toList();
    }

    @Override
    public Deposito buscarDepositoPorId(Long idDeposito) {
        return depositoRepository.findById(idDeposito)
                .orElseThrow(() -> {
                    log.error("Deposito {} no encontrado", idDeposito);
                    return new RuntimeException();
                });
    }

    @Override
    public List<Deposito> buscarDepositos() {
        return depositoRepository.findAll();
    }
}
