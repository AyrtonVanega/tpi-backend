package ar.edu.utn.frc.backend.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // ===== ROUTES DE SERVICIO DEPOSITOS =====
                .route("depositos", r -> r.path("/api/depositos/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://depositos"))
                .route("estadias-deposito", r -> r.path("/api/estadias-deposito/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://depositos"))
                .route("ubicaciones", r -> r.path("/api/ubicaciones/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://depositos"))
                // ===== ROUTES DE SERVICIO PERSONAS =====
                .route("camiones", r -> r.path("/api/camiones/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://personas"))
                .route("clientes", r -> r.path("/api/clientes/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://personas"))
                .route("transportistas", r -> r.path("/api/transportistas/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://personas"))
                // ===== ROUTES DE SERVICIO RUTAS =====
                .route("rutas", r -> r.path("/api/rutas/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://rutas"))
                // ===== ROUTES DE SERVICIO SOLICITUDES =====
                .route("contenedores", r -> r.path("/api/contenedores/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://solicitudes"))
                .route("solicitudes", r -> r.path("/api/solicitudes/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://solicitudes"))
                // ===== ROUTES DE SERVICIO TARIFAS =====
                .route("parametro-global", r -> r.path("/api/parametro-global/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://tarifas"))
                .route("tarifas", r -> r.path("/api/tarifas/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://tarifas"))
                .build();
    }
}
