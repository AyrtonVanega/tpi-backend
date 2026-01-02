package ar.edu.utn.frc.backend.depositos.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.depositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.model.Deposito;
import ar.edu.utn.frc.backend.depositos.service.base.CrudService;

public interface IDepositoService extends CrudService<DepositoResponseDto, DepositoRequestDto, Long> {

    Deposito buscarDepositoPorId(Long idDeposito);

    List<Deposito> buscarDepositos();

    List<DepositoResponseDto> obtenerDepositosEnBoundingBox(double minLat, double maxLat, double minLon, double maxLon);
}
