package ar.edu.utn.frc.backend.solicitudes.mapper;

public interface GenericMapper<T, ResponseDTO, RequestDTO> {
    T toEntity(RequestDTO requestDTO);

    ResponseDTO toResponse(T entity);
}
