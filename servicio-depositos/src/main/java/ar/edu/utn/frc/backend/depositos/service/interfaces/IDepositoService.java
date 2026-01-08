package ar.edu.utn.frc.backend.depositos.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.depositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.model.Deposito;

public interface IDepositoService {

    void crear(DepositoRequestDto depositoRequestDto);

    void actualizar(Long idDeposito, DepositoRequestDto depositoRequestDto);

    void eliminar(Long idDeposito);

    DepositoResponseDto obtenerPorId(Long idDeposito);

    List<DepositoResponseDto> obtenerTodos();

    Deposito buscarDepositoPorId(Long idDeposito);

    List<Deposito> buscarDepositos();

    List<DepositoResponseDto> obtenerDepositosEnBoundingBox(double minLat, double maxLat, double minLon, double maxLon);
}
