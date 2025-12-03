package ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces;

import ar.edu.utn.frc.backend.ServicioDepositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.model.Deposito;
import ar.edu.utn.frc.backend.ServicioDepositos.service.base.CrudService;

import java.util.List;

public interface IDepositoService extends CrudService<DepositoResponseDto, DepositoRequestDto, Long> {

    Deposito buscarDepositoPorId(Long idDeposito);

    List<Deposito> buscarDepositos();
}
