package me.donghun.gatewayserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@SpringBootApplication
@Controller
class GatewayServerApplication {
    val apiServer = "http://localhost:8080/"

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route {
                it.path("/signin").uri(apiServer)
            }
            .route {
                it.path("/signup").uri(apiServer)
            }

            .route {
                it.method(HttpMethod.GET)
                    .and()
                    .header("Authorization")
                    .and()
                    .path("/menus").filters {
                        f -> f.filter(AuthFilter().apply(UserRole.USER))
                }.uri(apiServer)
            }

            .route {
                it.method(HttpMethod.POST)
                    .and()
                    .header("Authorization")
                    .and()
                    .path("/menus").filters {
                        f -> f.filter(AuthFilter().apply(UserRole.OWNER))
                }.uri(apiServer)
            }
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<GatewayServerApplication>(*args)
}