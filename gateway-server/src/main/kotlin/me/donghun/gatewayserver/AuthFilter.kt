package me.donghun.gatewayserver

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpStatus
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets

class AuthFilter: AbstractGatewayFilterFactory<UserRole>() {

    private val verifier = JWT.require(Algorithm.HMAC256("super-secret".toByteArray(StandardCharsets.UTF_8))).build()

    override fun apply(role: UserRole): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            val response = exchange.response

            val tokenString = request.headers["Authorization"]!![0].substringAfter(' ')
            val decodedJWT: DecodedJWT
            try {
                decodedJWT = verifier.verify(tokenString)
            } catch (e: Exception) {
                response.statusCode = HttpStatus.UNAUTHORIZED
                return@GatewayFilter response.setComplete()
            }

            if (!decodedJWT.getClaim("roles").asList(String::class.java).contains(role.name)) {
                response.statusCode = HttpStatus.UNAUTHORIZED
                return@GatewayFilter response.setComplete()
            }

            chain.filter(exchange).then(Mono.fromRunnable {
                println("API ${request.methodValue} ${request.path} is called")
            })
        }
    }

}