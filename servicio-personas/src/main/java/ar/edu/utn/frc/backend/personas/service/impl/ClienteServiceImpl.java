package ar.edu.utn.frc.backend.personas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.dto.ClienteRequestDto;
import ar.edu.utn.frc.backend.personas.dto.ClienteResponseDto;
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
        Cliente cliente = clienteMapper.toEntity(dto);
        clienteRepository.save(cliente);
    }

    @Override
    public void actualizar(String docCliente, String tipoDocCliente, ClienteRequestDto dto) {
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
        clienteMapper.updateFromDto(dto, cliente);

        // Actualiza el doc y tipoDoc


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
        PersonaId id = new PersonaId(docCliente, tipoDocCliente);

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(
                            "Cliente no encontrado - docCliente:{}, tipoDocCliente:{}",
                            id.getDoc(),
                            id.getTipoDoc());
                    return new RuntimeException();
                });
        
        return clienteMapper.toResponse(cliente);
    }

    @Override
    public List<ClienteResponseDto> obtenerTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clienteMapper.toResponseList(clientes);
    }
}
