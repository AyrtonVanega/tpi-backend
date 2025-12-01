package ar.edu.utn.frc.backend.ServicioDepositos.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.ServicioDepositos.model.Ubicacion;
import ar.edu.utn.frc.backend.ServicioDepositos.service.interfaces.IUbicacionService;

@Service
public class UbicacionServiceImpl implements IUbicacionService {

    public Ubicacion crear(Ubicacion ubicacion) {
        return ubicacion;
    }

    public Ubicacion actualizar(Long idUbicacion, Ubicacion ubicacion) {
        return ubicacion;
    }

    public void eliminar(Long idUbicacion) {}

    public Ubicacion obtenerPorId(Long idUbicacion) {
        return Ubicacion.builder().build();
    }

    public List<Ubicacion> obtenerTodos() {
        return List.of();
    }
}
