package ar.edu.utn.frc.backend.depositos.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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
import ar.edu.utn.frc.backend.depositos.service.interfaces.IEstadoEstadiaDepositoService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EstadiaDepositoServiceImpl implements IEstadiaDepositoService {

    private final IDepositoService depositoService;
    private final IEstadoEstadiaDepositoService estadoEstadiaService;

    private final EstadiaDepositoRepository estadiaDepositoRepository;

    private final EstadiaDepositoMapper estadiaMapper;

    @Override
    public void crear(CreateEstadiaDepositoDto dto) {
        EstadiaDepositoId id = new EstadiaDepositoId(dto.getIdDeposito(), dto.getIdSolicitud());
        Optional<EstadiaDeposito> estadiaDeposito = estadiaDepositoRepository.findById(id);

        if (estadiaDeposito != null) {
            log.error(
                    "Esta Estadia Deposito ya existe - idDeposito:{}, idSolicitud:{}",
                    id.getIdDeposito(),
                    id.getIdSolicitud());
            throw new RuntimeException();
        } else {
            EstadiaDeposito nuevaEstadia = estadiaMapper.toEntity(dto);
            nuevaEstadia.setIdEstadiaDeposito(id);

            // Resuelve la relación con Deposito
            Deposito deposito = depositoService.buscarDepositoPorId(dto.getIdDeposito());
            nuevaEstadia.setDeposito(deposito);

            // Setea el estado inicial
            EstadoEstadiaDeposito estado = estadoEstadiaService.buscarPorCodigo("ACTIVA");
            nuevaEstadia.setEstado(estado);

            estadiaDepositoRepository.save(nuevaEstadia);
        }
    }

    @Override
    public void finalizarEstadia(Long idDeposito, Long idSolicitud, PatchEstadiaDepositoDto dto) {
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

        // Setea la fecha de finalizacion
        if (estadiaDeposito.getFechaHoraEntrada().isAfter(dto.getFechaHoraSalida())) {
            log.error("La fecha y hora de salida no puede ser anterior a la de ingreso");
            throw new RuntimeException();
        }

        estadiaDeposito.setFechaHoraSalida(dto.getFechaHoraSalida());

        // Actualiza estado
        EstadoEstadiaDeposito estado = estadoEstadiaService.buscarPorCodigo("FINALIZADA");
        estadiaDeposito.setEstado(estado);

        estadiaDepositoRepository.save(estadiaDeposito);
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
    public List<EstadiaDepositoResponseDto> obtenerEstadiasActivas(Long idDeposito) {
        List<EstadiaDeposito> estadiasActivas = estadiaDepositoRepository
                .buscarPorDepositoYEstado(idDeposito, "ACTIVA");

        return estadiaMapper.toResponseList(estadiasActivas);
    }

    @Override
    public double calcularCostoTotal(Long idSolicitud) {
        List<EstadiaDeposito> estadias = estadiaDepositoRepository.findByIdSolicitud(idSolicitud);
        return estadias.stream()
                .mapToDouble(estadia -> {
                    LocalDateTime entrada = estadia.getFechaHoraEntrada();
                    LocalDateTime salida = estadia.getFechaHoraSalida();

                    if (entrada == null || salida == null) {
                        return 0;
                    }

                    long minutosTotales = ChronoUnit.MINUTES.between(entrada, salida);

                    // Menos de 3 horas no se cobra estadia
                    if (minutosTotales < 180) {
                        return 0.0;
                    }

                    long minutosPorDia = 24 * 60;
                    long diasCompletos = minutosTotales / minutosPorDia;
                    long minutosRestantes = minutosTotales % minutosPorDia;

                    long diasCobrados = diasCompletos;

                    // Primer dia minimo
                    if (diasCobrados == 0) {
                        diasCobrados = 1;
                    } else if (minutosRestantes > 180) {
                        diasCobrados++;
                    }

                    // Calcula el costo final de la estadia
                    return diasCobrados * estadia.getDeposito().getCostoEstadiaDiaria();
                })
                .sum();
    }
}
