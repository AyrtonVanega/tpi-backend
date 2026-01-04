package ar.edu.utn.frc.backend.rutas.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.backend.rutas.client.DepositoClient;
import ar.edu.utn.frc.backend.rutas.client.OsrmClient;
import ar.edu.utn.frc.backend.rutas.client.SolicitudClient;
import ar.edu.utn.frc.backend.rutas.client.TarifaClient;
import ar.edu.utn.frc.backend.rutas.client.dto.DepositoDto;
import ar.edu.utn.frc.backend.rutas.client.dto.OsrmRouteDto;
import ar.edu.utn.frc.backend.rutas.dto.CreateRutaDto;
import ar.edu.utn.frc.backend.rutas.dto.RutaTentativaDto;
import ar.edu.utn.frc.backend.rutas.dto.TramoTentativoDto;
import ar.edu.utn.frc.backend.rutas.mapper.RutaMapper;
import ar.edu.utn.frc.backend.rutas.model.Ruta;
import ar.edu.utn.frc.backend.rutas.repository.RutaRepository;
import ar.edu.utn.frc.backend.rutas.service.interfaces.IRutaService;
import ar.edu.utn.frc.backend.rutas.service.interfaces.ITramoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RutaServiceImpl implements IRutaService {

        private final OsrmClient osrmClient;
        private final DepositoClient depositoClient;
        private final TarifaClient tarifaClient;
        private final SolicitudClient solicitudClient;
        private final ITramoService tramoService;
        private final RutaRepository rutaRepository;
        private final RutaMapper rutaMapper;

        @Override
        public List<RutaTentativaDto> obtenerRutasTentativas(Long idOrigen, double latOrigen, double lonOrigen,
                        Long idDestino, double latDestino, double lonDestino, double costoKmBase,
                        double consumoCombustibleAprox) {

                List<RutaTentativaDto> rutasTentativas = new ArrayList<>();

                // Obtiene lat y lon, min y max
                double minLat = Math.min(latOrigen, latDestino);
                double maxLat = Math.max(latOrigen, latDestino);
                double minLon = Math.min(lonOrigen, lonDestino);
                double maxLon = Math.max(lonOrigen, lonDestino);

                // Busca depositos en BoundingBox
                List<DepositoDto> depositosEnBoundingBox = depositoClient.obtenerDepositosEnBoundingBox(minLat, maxLat,
                                minLon,
                                maxLon);

                // -------------------------------------------------
                // RUTA DIRECTA
                // -------------------------------------------------
                // Crea ruta directa
                RutaTentativaDto rutaDirecta = calcularRutaTentativa(idOrigen, latOrigen, lonOrigen, idDestino,
                                latDestino, lonDestino, new ArrayList<>(), costoKmBase, consumoCombustibleAprox);

                // Agrega la ruta directa a rutasTentativas
                rutasTentativas.add(rutaDirecta);

                // -------------------------------------------------
                // RUTA MEDIANA
                // -------------------------------------------------
                // Busca Depositos a 50km de la Ruta para desviarse
                List<DepositoDto> depositosEnRutaTentativaMediana = obtenerDepositosEnRutaTentativa(latOrigen,
                                lonOrigen,
                                latDestino, lonDestino, depositosEnBoundingBox, 50);

                // Crea la ruta mediana
                RutaTentativaDto rutaMediana = calcularRutaTentativa(idOrigen, latOrigen, lonOrigen, idDestino,
                                latDestino, lonDestino, depositosEnRutaTentativaMediana, costoKmBase,
                                consumoCombustibleAprox);

                // Si es distinta a la ruta directa la agrega a rutasTentativas
                if (!rutaMediana.equals(rutaDirecta)) {
                        rutasTentativas.add(rutaMediana);
                }

                // -------------------------------------------------
                // RUTA LARGA
                // -------------------------------------------------
                // Busca Depositos a 200km de la Ruta para desviarse
                List<DepositoDto> depositosEnRutaTentativaLarga = obtenerDepositosEnRutaTentativa(latOrigen, lonOrigen,
                                latDestino, lonDestino, depositosEnBoundingBox, 200);

                // Crea la ruta larga
                RutaTentativaDto rutaLarga = calcularRutaTentativa(idOrigen, latOrigen, lonOrigen, idDestino,
                                latDestino, lonDestino, depositosEnRutaTentativaLarga, costoKmBase,
                                consumoCombustibleAprox);

                // Si es distinta a la ruta mediana la agrega a rutasTentativas
                if (!rutaLarga.equals(rutaMediana)) {
                        rutasTentativas.add(rutaLarga);
                }

                return rutasTentativas;
        }

        public RutaTentativaDto calcularRutaTentativa(Long idOrigen, double latOrigen, double lonOrigen, Long idDestino,
                        double latDestino, double lonDestino, List<DepositoDto> depositosEnRuta,
                        double costoKmBase, double consumoCombustibleAprox) {

                // Arma las coordenadas
                List<String> waypoints = new ArrayList<>();
                waypoints.add(lonOrigen + "," + latOrigen); // Inicio
                if (!depositosEnRuta.isEmpty()) {
                        for (DepositoDto d : depositosEnRuta) {
                                waypoints.add(d.getLongitud() + "," + d.getLatitud()); // Intermedios ordenados
                        }
                }
                waypoints.add(lonDestino + "," + latDestino); // Fin

                // Calcula la Ruta con OSRM
                OsrmRouteDto route = osrmClient.calcularRutaConOsrm(waypoints);

                // Crea la Ruta
                RutaTentativaDto ruta = new RutaTentativaDto();

                // Obtiene el Valor del Litro de Combustible
                double valorLitroCombustible = tarifaClient.obtenerParametrosGlobales().getValorLitroCombustible();

                // Calcula los Tramos
                List<TramoTentativoDto> tramos = tramoService.calcularTramosTentativos(route, idOrigen, idDestino,
                                depositosEnRuta, costoKmBase, consumoCombustibleAprox, valorLitroCombustible);

                // Suma el Costo Estimado de todos los Tramos
                double costoEstimadoTramos = 0.0;

                for (TramoTentativoDto tramo : tramos) {
                        costoEstimadoTramos += tramo.getCostoEstimado();
                }

                // Setea los valores de Ruta
                ruta.setCantidadTramos(tramos.size());
                ruta.setCantidadDepositos(depositosEnRuta.size());
                ruta.setDistanciaTotal(route.getDistance() / 1000.0);
                ruta.setCostoEstimado(calcularCostoEstimadoTotal(tramos.size(), costoEstimadoTramos));
                ruta.setTiempoEstimado(route.getDuration() / 3600.0);
                ruta.setTramos(tramos);

                return ruta;
        }

        // Filtra y ordena los Depositos que se encuentra a una determinada distancia de
        // la Ruta Origen-Destino.
        public List<DepositoDto> obtenerDepositosEnRutaTentativa(double latOrigen, double lonOrigen, double latDestino,
                        double lonDestino, List<DepositoDto> depositosEnBoundingBox, double distancia) {

                List<DepositoDto> depositosEnRutaTentativa = new ArrayList<>();

                for (DepositoDto d : depositosEnBoundingBox) {
                        // Calcula los 3 lados (Origen-Destino, Origen-Deposito y Deposito-Destino)
                        // mediante Pitagoras
                        // Multiplica por 111.32 para trabajar en KM si x,y son lon/lat
                        double base = Math.hypot(lonDestino - lonOrigen, latDestino - latOrigen) * 111.32;
                        double lado1 = Math.hypot(d.getLongitud() - lonOrigen, d.getLatitud() - latOrigen) * 111.32;
                        double lado2 = Math.hypot(lonDestino - d.getLongitud(), latDestino - d.getLatitud()) * 111.32;

                        // Calcula el Semiperímetro del Triangulo
                        double s = (base + lado1 + lado2) / 2.0;

                        // Calcula el Area del Triangulo
                        double area = Math.sqrt(s * (s - base) * (s - lado1) * (s - lado2));

                        // Calcula la Altura del Triangulo
                        double altura = (2 * area) / base;

                        // Valida la distancia y que no este "fuera" del segmento (proyección)
                        // Si (lado1^2 + base^2 < lado2^2) o (lado2^2 + base^2 < lado1^2), es un
                        // triángulo obtuso y la proyección cae fuera.
                        boolean esObtuso = (lado1 * lado1 + base * base < lado2 * lado2)
                                        || (lado2 * lado2 + base * base < lado1 * lado1);

                        if ((altura <= distancia) && !esObtuso)
                                depositosEnRutaTentativa.add(d);
                }

                // Ordena los Depositos por cercania al Origen
                return depositosEnRutaTentativa.stream()
                                .sorted(Comparator.comparingDouble(d -> Math.hypot(d.getLongitud() - lonOrigen,
                                                d.getLatitud() - latOrigen)))
                                .collect(Collectors.toList());
        }

        @Override
        public double calcularCostoEstimadoTotal(int cantTramos, double costoEstimadoTramos) {
                double costoGestionBase = tarifaClient.obtenerParametrosGlobales().getCostoGestionBase();
                return costoGestionBase * cantTramos + costoEstimadoTramos;
        }

        @Override
        public void crear(CreateRutaDto dto) {
                // Mapea datos simples DTO -> Entity
                Ruta ruta = rutaMapper.toEntity(dto.getRutaSeleccionada());

                // Guarda en la BD
                rutaRepository.save(ruta);

                // Crea los Tramos
                tramoService.crearTramos(ruta, dto.getRutaSeleccionada().getTramos());

                // Asigna la Ruta a la Solicitud
                solicitudClient.asignarRuta(dto.getIdSolicitud(), ruta.getIdRuta(), ruta.getCostoEstimado(),
                                ruta.getTiempoEstimado());
        }
}
