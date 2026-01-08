package ar.edu.utn.frc.backend.rutas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.rutas.dto.CreateRutaDto;
import ar.edu.utn.frc.backend.rutas.dto.RutaResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.RutaTentativaDto;
import ar.edu.utn.frc.backend.rutas.model.DetalleCostoRuta;
import ar.edu.utn.frc.backend.rutas.model.Ruta;

public interface IRutaService {

    List<RutaTentativaDto> obtenerRutasTentativas(Long idOrigen, double latOrigen, double lonOrigen, Long idDestino,
            double latDestino, double lonDestino, double costoKmBase, double consumoCombustibleAprox);

    double calcularCostoEstimadoTotal(int cantTramos, double costoEstimadoTramos);

    void crear(CreateRutaDto dto);

    RutaResponseDto obtenerPorId(Long idRuta);

    void finalizarRuta(Ruta ruta, double costoGestionBase, double costoTotalEstadias);

    double calcularCostoRealTotal(List<DetalleCostoRuta> detallesCostoRuta);

    double calcularTiempoReal(Ruta ruta);
}
