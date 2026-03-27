package ar.edu.utn.frc.backend.depositos.service.impl;

import java.util.List;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.depositos.dto.UbicacionRequestDto;
import ar.edu.utn.frc.backend.depositos.dto.UbicacionResponseDto;
import ar.edu.utn.frc.backend.depositos.exception.ResourceNotFoundException;
import ar.edu.utn.frc.backend.depositos.mapper.GeometryMapper;
import ar.edu.utn.frc.backend.depositos.mapper.UbicacionMapper;
import ar.edu.utn.frc.backend.depositos.model.Ubicacion;
import ar.edu.utn.frc.backend.depositos.repository.UbicacionRepository;
import ar.edu.utn.frc.backend.depositos.service.interfaces.IUbicacionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UbicacionServiceImpl implements IUbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final UbicacionMapper ubicacionMapper;
    private final GeometryMapper geometryMapper;

    @Override
    public Ubicacion crearSiNoExiste(UbicacionRequestDto dto) {
        // Crea el Point a partir de las coordenadas recibidas
        Point punto = geometryMapper.toPoint(dto.getLatitud(), dto.getLongitud());

        // Busca en la BD la Ubicacion por coordenadas (latitud y longitud),
        // si no la encuentra la crea
        Ubicacion ubicacion = ubicacionRepository.buscarPorCoordenadasAprox(punto, 10)
                .orElseGet(() -> {
                    Ubicacion u = Ubicacion.builder()
                            .direccion(dto.getDireccion())
                            .coordenadas(punto)
                            .nombreCiudad(dto.getNombreCiudad())
                            .build();
                    return ubicacionRepository.save(u);
                });
        return ubicacion;
    }

    @Override
    public void actualizar(Long idUbicacion, UbicacionRequestDto ubicacionRequestDto) {
        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Ubicacion " + idUbicacion + " no encontrada");
                });

        ubicacionMapper.updateFromDto(ubicacionRequestDto, ubicacion);
        ubicacionRepository.save(ubicacion);
    }

    @Override
    public void eliminar(Long idUbicacion) {
        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Ubicacion " + idUbicacion + " no encontrada");
                });

        ubicacionRepository.delete(ubicacion);
    }

    @Override
    public UbicacionResponseDto obtenerPorId(Long idUbicacion) {
        Ubicacion ubicacion = ubicacionRepository.findById(idUbicacion)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Ubicacion " + idUbicacion + " no encontrada");
                });

        return ubicacionMapper.toResponse(ubicacion);
    }

    @Override
    public List<UbicacionResponseDto> obtenerTodos() {
        List<Ubicacion> ubicaciones = ubicacionRepository.findAll();
        return ubicacionMapper.toResponseList(ubicaciones);
    }
}
