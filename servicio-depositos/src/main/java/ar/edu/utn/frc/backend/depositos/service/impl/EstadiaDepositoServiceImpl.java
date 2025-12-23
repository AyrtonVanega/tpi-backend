package ar.edu.utn.frc.backend.depositos.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.depositos.dto.CreateEstadiaDepositoDto;
import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.dto.PatchEstadiaDepositoDto;
import ar.edu.utn.frc.backend.depositos.mapper.EstadiaDepositoMapper;
import ar.edu.utn.frc.backend.depositos.model.Deposito;
import ar.edu.utn.frc.backend.depositos.model.EstadiaDeposito;
import ar.edu.utn.frc.backend.depositos.model.EstadiaDepositoId;
import ar.edu.utn.frc.backend.depositos.model.EstadoEstadiaDeposito;
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
    public void crear(CreateEstadiaDepositoDto dto) {
        EstadiaDeposito estadiaDeposito = estadiaMapper.toEntity(dto);

        // Setear la PK embebida
        EstadiaDepositoId id = new EstadiaDepositoId(dto.getIdDeposito(), dto.getIdSolicitud());
        estadiaDeposito.setIdEstadiaDeposito(id);

        // Resolver relación con Deposito
        Deposito deposito = depositoService.buscarDepositoPorId(dto.getIdDeposito());
        estadiaDeposito.setDeposito(deposito);

        EstadoEstadiaDeposito estado = null;
        estadiaDeposito.setEstado(estado);

        estadiaDepositoRepository.save(estadiaDeposito);
    }

    @Override
    public void actualizarParcial(Long idDeposito, Long idSolicitud, PatchEstadiaDepositoDto dto) {
        EstadiaDepositoId idEstadiaDeposito = new EstadiaDepositoId(idDeposito, idSolicitud);

        EstadiaDeposito estadiaDeposito = estadiaDepositoRepository.findById(idEstadiaDeposito)
                .orElseThrow(() -> {
                    log.error(
                            "Estadia Deposito no encontrada - idDeposito:{}, idSolicitud:{}",
                            idEstadiaDeposito.getIdDeposito(),
                            idEstadiaDeposito.getIdSolicitud()
                    );
                    return new RuntimeException();
                });

        // Actualizar campos simples con el mapper
        estadiaMapper.updateFromPatchDto(dto, estadiaDeposito);

        EstadoEstadiaDeposito estado = null;
        estadiaDeposito.setEstado(estado);

        estadiaDepositoRepository.save(estadiaDeposito);
    }

    @Override
    public void eliminar(Long idDeposito, Long idSolicitud) {
        EstadiaDepositoId idEstadiaDeposito = new EstadiaDepositoId(idDeposito, idSolicitud);

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
    public EstadiaDepositoResponseDto obtenerPorId(Long idDeposito, Long idSolicitud) {
        EstadiaDepositoId idEstadiaDeposito = new EstadiaDepositoId(idDeposito, idSolicitud);

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
    public List<EstadiaDepositoResponseDto> obtenerEstadiasActivas() {
        List<EstadiaDeposito> estadiasActivas = estadiaDepositoRepository
                .findAllByEstado_Codigo("ACTIVA")
                .orElseThrow(() -> {
                    log.error("No se encontraron estadias activas");
                    return new RuntimeException();
                });
        
        return estadiaMapper.toResponseList(estadiasActivas);
    }
}
