package ar.edu.utn.frc.backend.ServicioPersonas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.ServicioPersonas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.ServicioPersonas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.ServicioPersonas.service.interfaces.ICamionService;

@Service
public class CamionServiceImpl implements ICamionService {

    @Override
    public CamionResponseDto crear(CamionRequestDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crear'");
    }

    @Override
    public CamionResponseDto actualizar(String id, CamionRequestDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    @Override
    public void eliminar(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    @Override
    public CamionResponseDto obtenerPorId(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerPorId'");
    }

    @Override
    public List<CamionResponseDto> obtenerTodos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerTodos'");
    }
}
