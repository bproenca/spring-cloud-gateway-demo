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
				.route(p -> p
				.path("/service1/**")
				.filters(f -> f
					.rewritePath("/service1/(?<path>.*)", "/${path}")
					.addRequestHeader("Hello1", "World1"))
					//.removeRequestHeader("Cookie")) // Prevents cookie being sent downstream
					.uri("http://service1:9091")) // Taking advantage of docker naming

				// Simple re-route from: /get to: http://httpbin.org/80
                // And adds a simple "hello:world" HTTP Header
				.route(p -> p
					.path("/get")
					.filters(f -> f.addRequestHeader("Hello3", "World3"))
					.uri("http://httpbin.org:80"))                            

				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}