package ar.edu.utn.frc.backend.ServicioSolicitudes.service.base;

import java.util.List;

public interface CrudService<ResponseDTO, RequestDTO, ID> {

    ResponseDTO crear(RequestDTO entity);

    ResponseDTO actualizar(ID id, RequestDTO entity);

    void eliminar(ID id);

    ResponseDTO obtenerPorId(ID id);

    List<ResponseDTO> obtenerTodos();
}
