package ar.edu.utn.frc.backend.depositos.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.depositos.dto.CreateEstadiaDepositoDto;
import ar.edu.utn.frc.backend.depositos.dto.EstadiaDepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.dto.PatchEstadiaDepositoDto;

public interface IEstadiaDepositoService {

        void crear(CreateEstadiaDepositoDto dto);

        void actualizarParcial(Long idDeposito, Long idSolicitud, PatchEstadiaDepositoDto dto);

        void eliminar(Long idDeposito, Long idSolicitud);

        EstadiaDepositoResponseDto obtenerPorId(Long idDeposito, Long idSolicitud);

        List<EstadiaDepositoResponseDto> obtenerEstadiasActivas();
}
