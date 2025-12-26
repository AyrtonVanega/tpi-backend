package ar.edu.utn.frc.backend.personas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.personas.dto.PutTransportistaDto;
import ar.edu.utn.frc.backend.personas.dto.TransportistaRequestDto;
import ar.edu.utn.frc.backend.personas.dto.TransportistaResponseDto;
import ar.edu.utn.frc.backend.personas.model.Transportista;

public interface ITransportistaService {

    void crear(TransportistaRequestDto dto);

    void actualizar(String docTransportista, String tipoDocTransportista, PutTransportistaDto dto);

    void eliminar(String docTransportista, String tipoDocTransportista);

    TransportistaResponseDto obtenerPorId(String docTransportista, String tipoDocTransportista);

    List<TransportistaResponseDto> obtenerTodos();

    Transportista obtenerTransportistaPorId(String docTransportista, String tipoDocTransportista);
}
