package chatcore.gateway.configurations;

import chatcore.gateway.filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator routeLocator(
            RouteLocatorBuilder route,
            ServiceUrlsProperties props,
            AuthenticationFilter authFilter,
            @Value("${server.api.prefix}") String apiPrefix
    ) {
        return route.routes()
                .route(props.getSportsUser() + "-route-auth", r -> r
                        .path(apiPrefix + "/auth/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .circuitBreaker(c -> c
                                        .setName(props.getSportsUser() + "-circuit-breaker")
                                        .setFallbackUri(URI.create("forward:/fallback"))
                                )
                        )
                        .uri("lb://" + props.getSportsUser())
                )
                .route(props.getSportsUser() + "-route-users", r -> r
                        .path(apiPrefix + "/users/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .circuitBreaker(c -> c
                                        .setName(props.getSportsUser() + "-circuit-breaker")
                                        .setFallbackUri(URI.create("forward:/fallback"))
                                )
                                .filter(authFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri("lb://" + props.getSportsUser())
                )
                .route(props.getSportsOrder() + "-route", r -> r
                        .path(apiPrefix + "/cities/**",
                                apiPrefix + "/victims/**",
                                apiPrefix + "/orders/**",
                                apiPrefix + "/mutilations/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .circuitBreaker(cb -> cb
                                        .setName(props.getSportsOrder() + "-circuit-breaker")
                                        .setFallbackUri(URI.create("forward:/fallback"))
                                )
                                 .filter(authFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri("lb://" + props.getSportsOrder())
                )
                .route(props.getSportsFight() + "-route", r -> r
                        .path(apiPrefix + "/performers/**",
                                apiPrefix + "/fights/**")
                        .filters(f -> f
                                .stripPrefix(2)
                                .circuitBreaker(cb -> cb
                                        .setName(props.getSportsFight() + "-circuit-breaker")
                                        .setFallbackUri(URI.create("forward:/fallback"))
                                )
                                .filter(authFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri("lb://" + props.getSportsFight())
                )
                .build();
    }
}