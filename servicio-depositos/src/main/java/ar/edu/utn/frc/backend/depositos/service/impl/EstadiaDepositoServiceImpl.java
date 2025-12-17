package ar.edu.utn.frc.backend.depositos.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.mapper.EstadiaDepositoMapper;
import ar.edu.utn.frc.backend.depositos.model.Deposito;
import ar.edu.utn.frc.backend.depositos.model.EstadiaDeposito;
import ar.edu.utn.frc.backend.depositos.model.EstadiaDepositoId;
import ar.edu.utn.frc.backend.depositos.repository.EstadiaDepositoRepository;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IDepositoService;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IEstadiaDepositoService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstadiaDepositoServiceImpl implements IEstadiaDepositoService {

    private final IDepositoService depositoService;

    private final EstadiaDepositoRepository estadiaDepositoRepository;

    private final EstadiaDepositoMapper estadiaMapper;

    @Override
    public EstadiaDepositoResponseDto crear(EstadiaDepositoRequestDto dto) {
        EstadiaDeposito estadiaDeposito = estadiaMapper.toEntity(dto);
        estadiaDepositoRepository.save(estadiaDeposito);
        return estadiaMapper.toResponse(estadiaDeposito);
    }

    @Override
    public EstadiaDepositoResponseDto actualizar(EstadiaDepositoId idEstadiaDeposito, EstadiaDepositoRequestDto dto) {
        EstadiaDeposito estadiaDeposito = estadiaDepositoRepository.findById(idEstadiaDeposito)
                .orElseThrow(() -> {
                    log.error(
                            "Estadia Deposito no encontrada - idDeposito:{}, idSolicitud:{}",
                            idEstadiaDeposito.getIdDeposito(),
                            idEstadiaDeposito.getIdSolicitud()
                    );
                    return new RuntimeException();
                });

        Deposito deposito = depositoService.buscarDepositoPorId(dto.getIdDeposito());
        //EstadoEstadiaDeposito estado = estadoService.buscarEstadoPorId(dto.getIdEstado());

        estadiaDeposito.setDeposito(deposito);
        estadiaDeposito.setIdSolicitud(dto.getIdSolicitud());
        estadiaDeposito.setFechaHoraEntrada(dto.getFechaHoraEntrada());
        estadiaDeposito.setFechaHoraSalida(dto.getFechaHoraSalida());
        //estadiaDeposito.setEstado(estado);

        estadiaDepositoRepository.save(estadiaDeposito);

        return estadiaMapper.toResponse(estadiaDeposito);
    }

    @Override
    public void eliminar(EstadiaDepositoId idEstadiaDeposito) {
        EstadiaDeposito estadiaDeposito = estadiaDepositoRepository.findById(idEstadiaDeposito)
                .orElseThrow(() -> {
                    log.error(
                            "Estadia Deposito no encontrada - idDeposito:{}, idSolicitud:{}",
                            idEstadiaDeposito.getIdDeposito(),
                            idEstadiaDeposito.getIdSolicitud()
                    );
                    return new RuntimeException();
                });
        estadiaDepositoRepository.delete(estadiaDeposito);
    }

    @Override
    public EstadiaDepositoResponseDto obtenerPorId(EstadiaDepositoId idEstadiaDeposito) {
        EstadiaDeposito estadiaDeposito = estadiaDepositoRepository.findById(idEstadiaDeposito)
                .orElseThrow(() -> {
                    log.error(
                            "Estadia Deposito no encontrada - idDeposito:{}, idSolicitud:{}",
                            idEstadiaDeposito.getIdDeposito(),
                            idEstadiaDeposito.getIdSolicitud()
                    );
                    return new RuntimeException();
                });
        return estadiaMapper.toResponse(estadiaDeposito);
    }

    @Override
    public List<EstadiaDepositoResponseDto> obtenerTodos() {
        List<EstadiaDeposito> estadias = estadiaDepositoRepository.findAll();
        return estadias.stream()
                .map(estadiaMapper::toResponse)
                .toList();
    }
}
