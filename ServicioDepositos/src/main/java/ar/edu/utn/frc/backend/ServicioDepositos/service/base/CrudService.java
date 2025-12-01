package ar.edu.utn.frc.backend.ServicioDepositos.service.base;

import java.util.List;

public interface CrudService<T, ID> {
    T crear(T entity);
    T actualizar(ID id, T entity);
    void eliminar(ID id);
    T obtenerPorId(ID id);
    List<T> obtenerTodos();
}

