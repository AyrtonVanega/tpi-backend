package ar.edu.utn.frc.backend.rutas.service.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import ar.edu.utn.frc.backend.rutas.client.dto.CamionDto;
import ar.edu.utn.frc.backend.rutas.client.dto.DepositoDto;
import ar.edu.utn.frc.backend.rutas.client.dto.OsrmRouteDto;
import ar.edu.utn.frc.backend.rutas.dto.CostoTramoResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.PatchTramoDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoResponseDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoTentativoDto;
import ar.edu.utn.frc.backend.rutas.model.DetalleCostoTramo;
import ar.edu.utn.frc.backend.rutas.model.Ruta;
import ar.edu.utn.frc.backend.rutas.model.Tramo;

public interface ITramoService {

        List<TramoTentativoDto> calcularTramosTentativos(OsrmRouteDto route, Long idOrigen, Long idDestino,
                        List<DepositoDto> depositos, double costoKmBase, double consumoCombustibleAprox,
                        double valorLitroCombustible);

        double calcularCostoEstimado(double distancia, double costoKmBase, double consumoCombustibleAprox,
                        double valorLitroCombustible);

        void crearTramos(Ruta ruta, List<TramoTentativoDto> tramosDtos);

        void asignarCamion(Long idRuta, int orden, PatchTramoDto dto);

        List<TramoResponseDto> obtenerTodos(Ruta ruta);

        void iniciarTramo(Tramo tramo);

        void finalizarTramo(Tramo tramo, LocalDateTime fechaHora, CamionDto camion, double valorLitroCombustible);

        Tramo obtenerTramoPorId(Long idRuta, int orden);

        void validarInicioTramo(Tramo tramo);

        void validarFinalizacionTramo(Tramo tramo);

        double calcularCostoReal(List<DetalleCostoTramo> detallesCostoTramo);

        List<TramoResponseDto> obtenerTramosPorPatenteCamionYEstado(String patenteCamion, String estado);

        CostoTramoResponseDto mostrarCostos(Tramo tramo);
}
