package ar.edu.utn.frc.backend.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesDebugConfig {

    @Bean
    CommandLineRunner routesLogger(RouteLocator routeLocator) {
        return args -> routeLocator.getRoutes().subscribe(
                route -> System.out.println("Gateway route loaded: " + route.getId() + " -> " + route.getUri()));
    }
}