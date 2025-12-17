package ar.edu.utn.frc.backend.solicitudes.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorRequestDto;
import ar.edu.utn.frc.backend.solicitudes.dto.ContenedorResponseDto;
import ar.edu.utn.frc.backend.solicitudes.mapper.ContenedorMapper;
import ar.edu.utn.frc.backend.solicitudes.model.Contenedor;
import ar.edu.utn.frc.backend.solicitudes.repository.ContenedorRepository;
import ar.edu.utn.frc.backend.solicitudes.service.interfaces.IContenedorService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContenedorServiceImpl implements IContenedorService {

    private final ContenedorRepository contenedorRepository;
    private final ContenedorMapper contenedorMapper;

    @Override
    public ContenedorResponseDto crear(ContenedorRequestDto contenedorRequestDto) {
        Contenedor contenedor = contenedorMapper.toEntity(contenedorRequestDto);
        contenedorRepository.save(contenedor);
        return contenedorMapper.toResponse(contenedor);
    }

    @Override
    public ContenedorResponseDto actualizar(Long idContenedor, ContenedorRequestDto contenedorRequestDto) {
        Contenedor contenedor = contenedorRepository.findById(idContenedor)
                .orElseThrow(() -> {
                    log.error("Contenedor {} no encontrado", idContenedor);
                    return new RuntimeException();
                });

        contenedor.setAncho(contenedorRequestDto.getAncho());
        contenedor.setAlto(contenedorRequestDto.getAlto());
        contenedor.setLargo(contenedorRequestDto.getLargo());
        contenedor.setPeso(contenedorRequestDto.getPeso());

        contenedorRepository.save(contenedor);

        return contenedorMapper.toResponse(contenedor);
    }

    @Override
    public void eliminar(Long idContenedor) {
        Contenedor contenedor = contenedorRepository.findById(idContenedor)
                .orElseThrow(() -> {
                    log.error("Contenedor {} no encontrado", idContenedor);
                    return new RuntimeException();
                });

        contenedorRepository.delete(contenedor);
    }

    @Override
    public ContenedorResponseDto obtenerPorId(Long idContenedor) {
        Contenedor contenedor = contenedorRepository.findById(idContenedor)
                .orElseThrow(() -> {
                    log.error("Contenedor {} no encontrado", idContenedor);
                    return new RuntimeException();
                });
        return contenedorMapper.toResponse(contenedor);
    }

    @Override
    public List<ContenedorResponseDto> obtenerTodos() {
        List<Contenedor> contenedores = contenedorRepository.findAll();
        return contenedores.stream()
                .map(contenedorMapper::toResponse)
                .toList();
    }
}
