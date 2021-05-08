package com.scg.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("resource", r -> r
					.path("/resource")
					.filters(f -> f
						.addRequestHeader("Hello1", "World1"))
						//.removeRequestHeader("Cookie")) // Prevents cookie being sent downstream
					.uri("http://resource:9091")) // Taking advantage of docker naming
				.route("open", r -> r
					.path("/open")
					.filters(f -> f
						.addRequestHeader("Hello2", "World2"))
						//.removeRequestHeader("Cookie")) // Prevents cookie being sent downstream
					.uri("http://resource:9091")) // Taking advantage of docker naming
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}