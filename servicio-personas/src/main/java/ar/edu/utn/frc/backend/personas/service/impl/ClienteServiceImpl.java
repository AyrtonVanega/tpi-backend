package ar.edu.utn.frc.backend.personas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.dto.ClienteRequestDto;
import ar.edu.utn.frc.backend.personas.dto.ClienteResponseDto;
import ar.edu.utn.frc.backend.personas.dto.PutClienteDto;
import ar.edu.utn.frc.backend.personas.mapper.ClienteMapper;
import ar.edu.utn.frc.backend.personas.model.Cliente;
import ar.edu.utn.frc.backend.personas.model.PersonaId;
import ar.edu.utn.frc.backend.personas.repository.ClienteRepository;
import ar.edu.utn.frc.backend.personas.service.interfaces.IClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public void crear(ClienteRequestDto dto) {
        // Mapea datos simples DTO -> Entity
        Cliente cliente = clienteMapper.toEntity(dto);

        // Crea y setea el id
        PersonaId id = new PersonaId(dto.getDocCliente(), dto.getTipoDocCliente());
        cliente.setIdPersona(id);

        // Guarda en la BD
        clienteRepository.save(cliente);
    }

    @Override
    public void actualizar(String docCliente, String tipoDocCliente, PutClienteDto dto) {
        // Compone el id y busca el Cliente en la BD
        PersonaId id = new PersonaId(docCliente, tipoDocCliente);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(
                            "Cliente no encontrado - docCliente:{}, tipoDocCliente:{}",
                            id.getDoc(),
                            id.getTipoDoc());
                    return new RuntimeException();
                });
        
        // Actualiza datos simples
        clienteMapper.updateFromPutDto(dto, cliente);

        // Guarda los cambios
        clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(String docCliente, String tipoDocCliente) {
        PersonaId id = new PersonaId(docCliente, tipoDocCliente);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(
                            "Cliente no encontrado - docCliente:{}, tipoDocCliente:{}",
                            id.getDoc(),
                            id.getTipoDoc());
                    return new RuntimeException();
                });

        clienteRepository.delete(cliente);
    }

    @Override
    public ClienteResponseDto obtenerPorId(String docCliente, String tipoDocCliente) {
        // Compone el id y busca el Cliente en la BD
        PersonaId id = new PersonaId(docCliente, tipoDocCliente);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(
                            "Cliente no encontrado - docCliente:{}, tipoDocCliente:{}",
                            id.getDoc(),
                            id.getTipoDoc());
                    return new RuntimeException();
                });

        // Mapea datos simples Entity -> DTO
        ClienteResponseDto responseDto = clienteMapper.toResponse(cliente);

        // Setea el id al ResponseDto
        responseDto.setDocCliente(cliente.getIdPersona().getDoc());
        responseDto.setTipoDocCliente(cliente.getIdPersona().getTipoDoc());

        return responseDto;
    }

    @Override
    public List<ClienteResponseDto> obtenerTodos() {
        // Obtiene todos los clientes de la BD
        List<Cliente> clientes = clienteRepository.findAll();

        // Mapea datos simples Entity -> DTO
        List<ClienteResponseDto> responseDtoList = clienteMapper.toResponseList(clientes);

        // Setea doc y tipoDoc del Cliente a cada ResponseDto de la lista
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            ClienteResponseDto dto = responseDtoList.get(i);

            dto.setDocCliente(cliente.getIdPersona().getDoc());
            dto.setTipoDocCliente(cliente.getIdPersona().getTipoDoc());
        }

        return responseDtoList;
    }
}
