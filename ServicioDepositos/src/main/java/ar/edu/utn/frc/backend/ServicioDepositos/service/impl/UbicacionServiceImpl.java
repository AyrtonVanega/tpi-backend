package ar.edu.utn.frc.backend.ServicioDepositos.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.ServicioDepositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.ServicioDepositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.ServicioDepositos.mapper.UbicacionMapper;
import ar.edu.utn.frc.backend.ServicioDepositos.model.Ubicacion;
import ar.edu.utn.frc.backend.ServicioDepositos.repository.UbicacionRepository;
import ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces.IUbicacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UbicacionServiceImpl implements IUbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final UbicacionMapper ubicacionMapper;

    @Override
    public UbicacionResponseDto crear(UbicacionRequestDto ubicacionRequestDto) {
        Ubicacion ubicacion = ubicacionMapper.toEntity(ubicacionRequestDto);
        ubicacionRepository.save(ubicacion);
        return ubicacionMapper.toResponse(ubicacion);
    }

    @Override
    public UbicacionResponseDto actualizar(Long idUbicacion, UbicacionRequestDto ubicacionRequestDto) {
        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
                .orElseThrow(() -> {
                    log.error("Ubicacion {} no encontrada", idUbicacion);
                    return new RuntimeException();
                });

        ubicacion.setDireccion(ubicacionRequestDto.getDireccion());
        ubicacion.setLatitud(ubicacionRequestDto.getLatitud());
        ubicacion.setLongitud(ubicacionRequestDto.getLongitud());
        ubicacion.setNombreCiudad(ubicacionRequestDto.getNombreCiudad());

        ubicacionRepository.save(ubicacion);

        return ubicacionMapper.toResponse(ubicacion);
    }

    @Override
    public void eliminar(Long idUbicacion) {
        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
                .orElseThrow(() -> {
                    log.error("Ubicacion {} no encontrada", idUbicacion);
                    return new RuntimeException();
                });

        ubicacionRepository.delete(ubicacion);
    }

    @Override
    public UbicacionResponseDto obtenerPorId(Long idUbicacion) {
        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
                .orElseThrow(() -> {
                    log.error("Ubicacion {} no encontrada", idUbicacion);
                    return new RuntimeException();
                });

        return ubicacionMapper.toResponse(ubicacion);
    }

    @Override
    public List<UbicacionResponseDto> obtenerTodos() {
        List<Ubicacion> ubicaciones = ubicacionRepository.findAll();
        return ubicaciones.stream()
                .map(ubicacionMapper::toResponse)
                .toList();
    }
}
