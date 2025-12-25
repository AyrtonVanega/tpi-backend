package ar.edu.utn.frc.backend.personas.service.base;

import java.util.List;

public interface CrudService<ResponseDTO, RequestDTO, ID> {
    
    void crear(RequestDTO entity);

    void actualizar(ID id, RequestDTO entity);

    void eliminar(ID id);

    ResponseDTO obtenerPorId(ID id);

    List<ResponseDTO> obtenerTodos();
}
