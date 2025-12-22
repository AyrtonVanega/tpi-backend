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
                .route("estados-estadias-deposito", r -> r.path("/api/estados-estadias-deposito/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://depositos"))
                .route("ubicaciones", r -> r.path("/api/ubicaciones/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://depositos"))
                .build();
    }
}
