package ar.edu.utn.frc.backend.rutas.service.interfaces;

import java.util.List;

import ar.edu.utn.frc.backend.rutas.client.dto.DepositoDto;
import ar.edu.utn.frc.backend.rutas.client.dto.OsrmRouteDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoTentativoDto;
import ar.edu.utn.frc.backend.rutas.model.Ruta;

public interface ITramoService {

        List<TramoTentativoDto> calcularTramosTentativos(OsrmRouteDto route, Long idOrigen, Long idDestino,
                        List<DepositoDto> depositos, double costoKmBase, double consumoCombustibleAprox,
                        double valorLitroCombustible);

        double calcularCostoEstimado(double distancia, double costoKmBase, double consumoCombustibleAprox,
                        double valorLitroCombustible);

        void crearTramos(Ruta ruta, List<TramoTentativoDto> tramosDtos);
}
