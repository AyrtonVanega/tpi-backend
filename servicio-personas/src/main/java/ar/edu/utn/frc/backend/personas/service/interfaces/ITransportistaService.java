package ar.edu.utn.frc.backend.personas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.personas.dto.TransportistaRequestDto;
import ar.edu.utn.frc.backend.personas.dto.TransportistaResponseDto;

public interface ITransportistaService {

    void crear(TransportistaRequestDto dto);

    void actualizar(String docTransportista, String tipoDocTransportista, TransportistaRequestDto dto);

    void eliminar(String docTransportista, String tipoDocTransportista);

    TransportistaResponseDto obtenerPorId(String docTransportista, String tipoDocTransportista);

    List<TransportistaResponseDto> obtenerTodos();
}
