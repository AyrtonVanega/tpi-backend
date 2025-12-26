package ar.edu.utn.frc.backend.personas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.dto.TransportistaRequestDto;
import ar.edu.utn.frc.backend.personas.dto.TransportistaResponseDto;
import ar.edu.utn.frc.backend.personas.mapper.TransportistaMapper;
import ar.edu.utn.frc.backend.personas.model.PersonaId;
import ar.edu.utn.frc.backend.personas.model.Transportista;
import ar.edu.utn.frc.backend.personas.repository.TransportistaRepository;
import ar.edu.utn.frc.backend.personas.service.interfaces.ICamionService;
import ar.edu.utn.frc.backend.personas.service.interfaces.ITransportistaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransportistaServiceImpl implements ITransportistaService {

    private final ICamionService camionService;
    private final TransportistaRepository transportistaRepository;
    private final TransportistaMapper transportistaMapper;

    @Override
    public void crear(TransportistaRequestDto dto) {
        Transportista transportista = transportistaMapper.toEntity(dto);

        PersonaId id = new PersonaId(dto.getDocTransportista(), dto.getTipoDocTransportista());
        transportista.setIdPersona(id);

        transportistaRepository.save(transportista);

        // Crea el camion si no existe
        camionService.crearSiNoExiste(
                dto.getPatenteCamion(),
                dto.getVolumenCamion(),
                dto.getPesoCamion(),
                dto.getCostoBaseKmCamion(),
                dto.getConsumoCombustiblePromedioCamion(),
                transportista);
    }

    @Override
    public void actualizar(String docTransportista, String tipoDocTransportista, TransportistaRequestDto dto) {
        PersonaId id = new PersonaId(docTransportista, tipoDocTransportista);

        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(
                            "Cliente no encontrado - docCliente:{}, tipoDocCliente:{}",
                            id.getDoc(),
                            id.getTipoDoc());
                    return new RuntimeException();
                });
        
        // Actualiza datos simples
        transportistaMapper.updateFromDto(dto, transportista);

        // Actualiza el doc y tipoDoc


        // Guarda los cambios
        transportistaRepository.save(transportista);
    }

    @Override
    public void eliminar(String docTransportista, String tipoDocTransportista) {
        PersonaId id = new PersonaId(docTransportista, tipoDocTransportista);

        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(
                            "Cliente no encontrado - docCliente:{}, tipoDocCliente:{}",
                            id.getDoc(),
                            id.getTipoDoc());
                    return new RuntimeException();
                });

        transportistaRepository.delete(transportista);
    }

    @Override
    public TransportistaResponseDto obtenerPorId(String docTransportista, String tipoDocTransportista) {
        PersonaId id = new PersonaId(docTransportista, tipoDocTransportista);

        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(
                            "Cliente no encontrado - docCliente:{}, tipoDocCliente:{}",
                            id.getDoc(),
                            id.getTipoDoc());
                    return new RuntimeException();
                });

        return transportistaMapper.toResponse(transportista);
    }

    @Override
    public List<TransportistaResponseDto> obtenerTodos() {
        List<Transportista> transportistas = transportistaRepository.findAll();
        return transportistaMapper.toResponseList(transportistas);
    }
}
