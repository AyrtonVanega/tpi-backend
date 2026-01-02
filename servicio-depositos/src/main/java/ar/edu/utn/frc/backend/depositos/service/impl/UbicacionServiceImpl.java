package ar.edu.utn.frc.backend.depositos.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.depositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.depositos.mapper.UbicacionMapper;
import ar.edu.utn.frc.backend.depositos.model.Ubicacion;
import ar.edu.utn.frc.backend.depositos.repository.UbicacionRepository;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IUbicacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UbicacionServiceImpl implements IUbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final UbicacionMapper ubicacionMapper;

    @Override
    public UbicacionResponseDto crearSiNoExiste(UbicacionRequestDto dto) {
        // Busca en la BD la Ubicacion por coordenadas (latitud y longitud),
        // si no la encuentra la crea
        Ubicacion ubicacion = ubicacionRepository.buscarPorCoordenadasAprox(dto.getLatitud(), dto.getLongitud())
                .orElseGet(() -> {
                    Ubicacion u = Ubicacion.builder()
                            .direccion(dto.getDireccion())
                            .latitud(dto.getLatitud())
                            .longitud(dto.getLongitud())
                            .nombreCiudad(dto.getNombreCiudad())
                            .build();
                    return ubicacionRepository.save(u);
                });
        return ubicacionMapper.toResponse(ubicacion);
    }

    @Override
    public void actualizar(Long idUbicacion, UbicacionRequestDto ubicacionRequestDto) {
        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
                .orElseThrow(() -> {
                    log.error("Ubicacion {} no encontrada", idUbicacion);
                    return new RuntimeException();
                });

        ubicacionMapper.updateFromDto(ubicacionRequestDto, ubicacion);
        ubicacionRepository.save(ubicacion);
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
        return ubicacionMapper.toResponseList(ubicaciones);
    }
}
