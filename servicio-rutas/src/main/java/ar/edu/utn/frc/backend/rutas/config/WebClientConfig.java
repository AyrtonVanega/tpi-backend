package ar.edu.utn.frc.backend.rutas.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient osrmWebClient() {
        return WebClient.builder()
                .baseUrl("http://osrm:5000")
                .build();
    }

    @Bean
    public WebClient depositoWebClient(
            WebClient.Builder builder,
            ClientRegistrationRepository repo,
            OAuth2AuthorizedClientRepository authRepo) {

        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                repo, authRepo);

        oauth.setDefaultClientRegistrationId("depositos-client");

        return builder
                .apply(oauth.oauth2Configuration())
                .build();
    }
}
