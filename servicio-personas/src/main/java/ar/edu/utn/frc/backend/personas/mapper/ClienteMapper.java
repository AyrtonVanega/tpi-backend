package ar.edu.utn.frc.backend.personas.mapper;

import org.springframework.stereotype.Component;

import ar.edu.utn.frc.backend.personas.dto.ClienteRequestDto;
import ar.edu.utn.frc.backend.personas.dto.ClienteResponseDto;
import ar.edu.utn.frc.backend.personas.model.Cliente;

@Component
public class ClienteMapper implements GenericMapper<Cliente, ClienteResponseDto, ClienteRequestDto> {

    @Override
    public Cliente toEntity(ClienteRequestDto requestDTO) {
        Cliente cliente = new Cliente();

        cliente.setNombre(requestDTO.getNombre());
        cliente.setApellido(requestDTO.getApellido());
        cliente.setTelefono(requestDTO.getTelefono());
        cliente.setEmail(requestDTO.getEmail());
        
        return cliente;
    }

    @Override
    public ClienteResponseDto toResponse(Cliente entity) {
        return ClienteResponseDto.builder()
                .doc(entity.getIdPersona().getDoc())
                .tipoDoc(entity.getIdPersona().getTipoDoc())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .telefono(entity.getTelefono())
                .email(entity.getEmail())
                .build();
    }
}
