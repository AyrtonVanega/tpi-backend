package ar.edu.utn.frc.backend.ServicioPersonas.mapper;

public interface GenericMapper<T, ResponseDTO, RequestDTO> {
    T toEntity(RequestDTO requestDTO);

    ResponseDTO toResponse(T entity);
}
