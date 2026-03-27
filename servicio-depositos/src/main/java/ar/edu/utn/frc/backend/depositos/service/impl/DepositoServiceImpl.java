package ar.edu.utn.frc.backend.depositos.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.depositos.dto.DepositoRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.DepositoResponseDto;
import ar.edu.utn.frc.backend.depositos.exception.ResourceNotFoundException;
import ar.edu.utn.frc.backend.depositos.mapper.DepositoMapper;
import ar.edu.utn.frc.backend.depositos.mapper.UbicacionMapper;
import ar.edu.utn.frc.backend.depositos.model.Deposito;
import ar.edu.utn.frc.backend.depositos.model.Ubicacion;
import ar.edu.utn.frc.backend.depositos.repository.DepositoRepository;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IDepositoService;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IUbicacionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositoServiceImpl implements IDepositoService {

    private final DepositoRepository depositoRepository;
    private final DepositoMapper depositoMapper;
    private final IUbicacionService ubicacionService;
    private final UbicacionMapper ubicacionMapper;

    @Override
    public void crear(DepositoRequestDto depositoRequestDto) {
        // Crea la Ubicacion del Deposito
        Ubicacion ubicacion = ubicacionService.crearSiNoExiste(depositoRequestDto.getUbicacion());

        // Mapea datos simples DTO -> Entity y setea la ubicacion
        Deposito deposito = depositoMapper.toEntity(depositoRequestDto);
        deposito.setUbicacion(ubicacion);

        depositoRepository.save(deposito);
    }

    @Override
    public void actualizar(Long idDeposito, DepositoRequestDto depositoRequestDto) {
        Deposito deposito = depositoRepository.findById(idDeposito)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Deposito " + idDeposito + " no encontrado");
                });

        depositoMapper.updateFromDto(depositoRequestDto, deposito);
        depositoRepository.save(deposito);
    }

    @Override
    public void eliminar(Long idDeposito) {
        Deposito deposito = depositoRepository.findById(idDeposito)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Deposito " + idDeposito + " no encontrado");
                });

        depositoRepository.delete(deposito);
    }

    @Override
    public DepositoResponseDto obtenerPorId(Long idDeposito) {
        Deposito deposito = depositoRepository.findById(idDeposito)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Deposito " + idDeposito + " no encontrado");
                });

        // Mapea datos simples Entity -> DTO
        DepositoResponseDto responseDto = depositoMapper.toResponse(deposito);

        // Mapea la ubicacion
        responseDto.setUbicacion(ubicacionMapper.toResponse(deposito.getUbicacion()));

        return responseDto;
    }

    @Override
    public List<DepositoResponseDto> obtenerTodos() {
        List<Deposito> depositos = depositoRepository.findAll();

        // Mapea datos simples Entity -> DTO
        List<DepositoResponseDto> responseDtoList = depositoMapper.toResponseList(depositos);

        // Mapea datos faltantes
        for (int i = 0; i < depositos.size(); i++) {
            Deposito deposito = depositos.get(i);
            DepositoResponseDto dto = responseDtoList.get(i);

            dto.setUbicacion(ubicacionMapper.toResponse(deposito.getUbicacion()));
        }

        return responseDtoList;
    }

    @Override
    public Deposito buscarDepositoPorId(Long idDeposito) {
        return depositoRepository.findById(idDeposito)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Deposito " + idDeposito + " no encontrado");
                });
    }

    @Override
    public List<Deposito> buscarDepositos() {
        return depositoRepository.findAll();
    }

    @Override
    public List<DepositoResponseDto> obtenerDepositosEnBoundingBox(double minLat, double maxLat, double minLon,
            double maxLon) {

        List<Deposito> depositos = depositoRepository.findDepositosEnBoundingBox(minLat, maxLat, minLon, maxLon);

        // Mapea datos simples Entity -> DTO
        List<DepositoResponseDto> responseDtoList = depositoMapper.toResponseList(depositos);

        // Mapea datos faltantes
        for (int i = 0; i < depositos.size(); i++) {
            Deposito deposito = depositos.get(i);
            DepositoResponseDto dto = responseDtoList.get(i);

            dto.setUbicacion(ubicacionMapper.toResponse(deposito.getUbicacion()));
        }

        return responseDtoList;
    }
}
