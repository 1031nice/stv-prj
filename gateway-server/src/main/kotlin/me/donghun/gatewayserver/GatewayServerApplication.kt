package me.donghun.gatewayserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication
class GatewayServerApplication {
    @Bean
    fun myRoutes(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route {
                it.path("/hi").filters {
                    f -> f.addRequestHeader("Hello", "World")
                }.uri("http://localhost:8080/hello")
            }
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<GatewayServerApplication>(*args)
}