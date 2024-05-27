package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("vaccination", r -> r.path("/vaccination/**")
                        .uri("http://localhost:8091"))
                .route("operateur", r -> r.path("/operateur/**")
                        .uri("http://localhost:8086"))
                .route("stock", r -> r.path("/stock/**")
                        .uri("http://localhost:8084"))
                .route("product", r -> r.path("/**")
                        .uri("http://localhost:8087"))
                .build();
    }

}
