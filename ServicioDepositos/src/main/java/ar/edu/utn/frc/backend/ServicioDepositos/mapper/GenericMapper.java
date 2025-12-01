package ar.edu.utn.frc.backend.ServicioDepositos.mapper;

public interface GenericMapper<T, RequestDTO, ResponseDTO> {

    T toEntity(RequestDTO dto);

    ResponseDTO toResponse(T entity);
}
