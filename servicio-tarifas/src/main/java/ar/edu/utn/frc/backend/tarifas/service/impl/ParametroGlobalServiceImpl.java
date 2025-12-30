package ar.edu.utn.frc.backend.tarifas.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.tarifas.dto.ParametroRequestDto;
import ar.edu.utn.frc.backend.tarifas.dto.ParametroResponseDto;
import ar.edu.utn.frc.backend.tarifas.mapper.ParametroMapper;
import ar.edu.utn.frc.backend.tarifas.model.ParametroGlobal;
import ar.edu.utn.frc.backend.tarifas.repository.ParametroGlobalRepository;
import ar.edu.utn.frc.backend.tarifas.service.interfaces.IParametroGlobalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParametroGlobalServiceImpl implements IParametroGlobalService {

    private final ParametroGlobalRepository parametroRepository;
    private final ParametroMapper parametroMapper;

    @Override
    public void actualizar(ParametroRequestDto dto) {
        ParametroGlobal parametro = parametroRepository.findById(1L)
                .orElseThrow(() -> {
                    log.error("Parametro no encontrado");
                    return new RuntimeException();
                });

        parametroMapper.updateFromDto(dto, parametro);

        parametro.setUltimaModificacion(LocalDate.now());

        parametroRepository.save(parametro);
    }

    @Override
    public ParametroResponseDto obtenerParametroGlobal() {
        ParametroGlobal parametro = parametroRepository.findById(1L)
                .orElseThrow(() -> {
                    log.error("Parametro no encontrado");
                    return new RuntimeException();
                });

        return parametroMapper.toResponse(parametro);
    }

}
