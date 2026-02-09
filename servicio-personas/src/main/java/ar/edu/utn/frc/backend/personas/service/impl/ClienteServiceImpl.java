package ar.edu.utn.frc.backend.personas.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.dto.ClienteResponseDto;
import ar.edu.utn.frc.backend.personas.dto.CreateClienteDto;
import ar.edu.utn.frc.backend.personas.dto.PutClienteDto;
import ar.edu.utn.frc.backend.personas.exception.ResourceNotFoundException;
import ar.edu.utn.frc.backend.personas.mapper.ClienteMapper;
import ar.edu.utn.frc.backend.personas.model.Cliente;
import ar.edu.utn.frc.backend.personas.model.PersonaId;
import ar.edu.utn.frc.backend.personas.repository.ClienteRepository;
import ar.edu.utn.frc.backend.personas.service.interfaces.IClienteService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    private final Map<PersonaId, Cliente> clientes = new HashMap<>();

    @Override
    public void crear(CreateClienteDto dto) {
        // Compone el id
        PersonaId personaId = new PersonaId(dto.getDocCliente(), dto.getTipoDocCliente());

        // Registra el cliente si no existe
        this.clientes.computeIfAbsent(personaId, id -> {
            // Mapea datos simples DTO -> Entity
            Cliente cliente = clienteMapper.toEntity(dto);

            // Setea el id
            cliente.setIdPersona(personaId);

            // Guarda en la BD
            return clienteRepository.save(cliente);
        });
    }

    @Override
    public void actualizar(String docCliente, String tipoDocCliente, PutClienteDto dto) {
        // Compone el id y busca el Cliente en la BD
        PersonaId id = new PersonaId(docCliente, tipoDocCliente);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Cliente no encontrado - docCliente: " + id.getDoc()
                            + ", tipoDocCliente: " + id.getTipoDoc());
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
                    return new ResourceNotFoundException("Cliente no encontrado - docCliente: " + id.getDoc()
                            + ", tipoDocCliente: " + id.getTipoDoc());
                });

        clienteRepository.delete(cliente);
    }

    @Override
    public ClienteResponseDto obtenerPorId(String docCliente, String tipoDocCliente) {
        // Compone el id y busca el Cliente en la BD
        PersonaId id = new PersonaId(docCliente, tipoDocCliente);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Cliente no encontrado - docCliente: " + id.getDoc()
                            + ", tipoDocCliente: " + id.getTipoDoc());
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
