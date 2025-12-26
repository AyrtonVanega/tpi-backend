package ar.edu.utn.frc.backend.personas.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.personas.dto.CreateTransportistaDto;
import ar.edu.utn.frc.backend.personas.dto.PutTransportistaDto;
import ar.edu.utn.frc.backend.personas.dto.TransportistaResponseDto;
import ar.edu.utn.frc.backend.personas.mapper.TransportistaMapper;
import ar.edu.utn.frc.backend.personas.model.Camion;
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
    public void crear(CreateTransportistaDto dto) {
        // Mapea datos simples DTO -> Entity
        Transportista transportista = transportistaMapper.toEntity(dto);

        // Crea y setea el id
        PersonaId id = new PersonaId(dto.getDocTransportista(), dto.getTipoDocTransportista());
        transportista.setIdPersona(id);

        // Crea el camion si no existe
        Camion camion = camionService.crearSiNoExiste(
                dto.getPatenteCamion(),
                dto.getVolumenCamion(),
                dto.getPesoCamion(),
                dto.getCostoBaseKmCamion(),
                dto.getConsumoCombustiblePromedioCamion(),
                transportista);
        
        // Setea el Camion
        transportista.setCamion(camion);

        // Guarda en la BD
        transportistaRepository.save(transportista);
    }

    @Override
    public void actualizar(String docTransportista, String tipoDocTransportista, PutTransportistaDto dto) {
        // Compone el id y busca el Transportista en la BD
        PersonaId id = new PersonaId(docTransportista, tipoDocTransportista);
        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(
                            "Transportista no encontrado - docTransportista:{}, tipoDocTransportista:{}",
                            id.getDoc(),
                            id.getTipoDoc());
                    return new RuntimeException();
                });
        
        // Actualiza datos simples
        transportistaMapper.updateFromPutDto(dto, transportista);

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
        // Compone el id y busca el Transportista en la BD
        PersonaId id = new PersonaId(docTransportista, tipoDocTransportista);
        Transportista transportista = transportistaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(
                            "Transportista no encontrado - docTransportista:{}, tipoDocTransportista:{}",
                            id.getDoc(),
                            id.getTipoDoc());
                    return new RuntimeException();
                });

        // Mapea datos simples Entity -> DTO
        TransportistaResponseDto responseDto = transportistaMapper.toResponse(transportista);

        // Setea el id al ResponseDto
        responseDto.setDocTransportista(transportista.getIdPersona().getDoc());
        responseDto.setTipoDocTransportista(transportista.getIdPersona().getTipoDoc());

        // Busca el camion perteneciente al transportista y lo setea al ResponseDto
        Camion camion = camionService.buscarCamionPorId(transportista.getCamion().getPatente());
        responseDto.setPatenteCamion(camion.getPatente());

        return responseDto;
    }

    @Override
    public List<TransportistaResponseDto> obtenerTodos() {
        // Obtiene todos los transportistas de la BD
        List<Transportista> transportistas = transportistaRepository.findAll();

        // Mapea datos simples Entity -> DTO
        List<TransportistaResponseDto> responseDtoList = transportistaMapper.toResponseList(transportistas);

        // Completa los campos faltantes en cada DTO
        for (int i = 0; i < transportistas.size(); i++) {
            Transportista transportista = transportistas.get(i);
            TransportistaResponseDto dto = responseDtoList.get(i);

            // Setea el id compuesto
            dto.setDocTransportista(transportista.getIdPersona().getDoc());
            dto.setTipoDocTransportista(transportista.getIdPersona().getTipoDoc());

            // Setea la patente del camión
            Camion camion = camionService.buscarCamionPorId(transportista.getCamion().getPatente());
            dto.setPatenteCamion(camion.getPatente());
        }

        return responseDtoList;
    }

    @Override
    public Transportista obtenerTransportistaPorId(String docTransportista, String tipoDocTransportista) {
        // Compone el id y busca el Transportista en la BD
        PersonaId id = new PersonaId(docTransportista, tipoDocTransportista);
        return transportistaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(
                            "Transportista no encontrado - docTransportista:{}, tipoDocTransportista:{}",
                            id.getDoc(),
                            id.getTipoDoc());
                    return new RuntimeException();
                });
    }
}
