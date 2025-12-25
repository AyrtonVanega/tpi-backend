package ar.edu.utn.frc.backend.personas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.personas.dto.CamionRequestDto;
import ar.edu.utn.frc.backend.personas.dto.CamionResponseDto;
import ar.edu.utn.frc.backend.personas.dto.PatchCamionDto;
import ar.edu.utn.frc.backend.personas.model.Camion;

public interface ICamionService {

    Camion crearSiNoExiste(String patente, double volumen, double peso, double costoBaseKm,
            double consumoCombustiblePromedio);

    void actualizar(String patenteCamion, CamionRequestDto dto);

    void eliminar(String patenteCamion);

    CamionResponseDto obtenerPorId(String patenteCamion);

    List<CamionResponseDto> obtenerCamionesDisponibles();

    void actualizarDisponibilidad(String patenteCamion, PatchCamionDto patchCamionDto);
}
