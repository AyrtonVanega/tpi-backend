package ar.edu.utn.frc.backend.personas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import ar.edu.utn.frc.backend.personas.dto.ClienteRequestDto;
import ar.edu.utn.frc.backend.personas.dto.ClienteResponseDto;
import ar.edu.utn.frc.backend.personas.dto.PutClienteDto;
import ar.edu.utn.frc.backend.personas.model.Cliente;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ClienteMapper {

    @Mapping(target = "idPersona", ignore = true)
    @Mapping(target = "solicitudes", ignore = true)
    Cliente toEntity(ClienteRequestDto dto);

    @Mapping(target = "docCliente", ignore = true)
    @Mapping(target = "tipoDocCliente", ignore = true)
    @Mapping(target = "idSolicitudes", ignore = true)
    ClienteResponseDto toResponse(Cliente entity);

    @Mapping(target = "idPersona", ignore = true)
    @Mapping(target = "solicitudes", ignore = true)
    void updateFromPutDto(PutClienteDto dto, @MappingTarget Cliente entity);

    List<ClienteResponseDto> toResponseList(List<Cliente> clientes);
}
