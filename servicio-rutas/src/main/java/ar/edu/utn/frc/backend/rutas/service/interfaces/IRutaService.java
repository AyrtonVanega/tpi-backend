package ar.edu.utn.frc.backend.rutas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.rutas.dto.RutaTentativaDto;

public interface IRutaService {

    List<RutaTentativaDto> obtenerRutasTentativas(Long idOrigen, double latOrigen, double lonOrigen, Long idDestino,
            double latDestino, double lonDestino, double costoKmBase, double consumoCombustibleAprox);

    double calcularCostoEstimadoTotal(int cantTramos, double costoEstimadoTramos);
}
