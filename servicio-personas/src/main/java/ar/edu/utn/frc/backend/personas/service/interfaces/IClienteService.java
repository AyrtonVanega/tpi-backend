package ar.edu.utn.frc.backend.personas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.personas.dto.ClienteRequestDto;
import ar.edu.utn.frc.backend.personas.dto.ClienteResponseDto;

public interface IClienteService {
    
    void crear(ClienteRequestDto dto);

    void actualizar(String docCliente, String tipoDocCliente, ClienteRequestDto dto);

    void eliminar(String docCliente, String tipoDocCliente);

    ClienteResponseDto obtenerPorId(String docCliente, String tipoDocCliente);

    List<ClienteResponseDto> obtenerTodos();
}
