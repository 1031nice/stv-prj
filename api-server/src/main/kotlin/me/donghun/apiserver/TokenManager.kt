package me.donghun.apiserver

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import me.donghun.apiserver.account.UserRole
import java.nio.charset.StandardCharsets
import java.util.*

object TokenManager {

    private const val hour = 60 * 60 * 1000L
    private val algorithm = Algorithm.HMAC256("super-secret".toByteArray(StandardCharsets.UTF_8))
    private val verifier = JWT.require(algorithm).build()

    fun generateAccessToken(username: String, role: UserRole): String {
        return generateToken(username, hour, role)
    }

    fun generateRefreshToken(username: String, role: UserRole): String {
        return generateToken(username, 24 * 7 * hour, role)
    }

    private fun generateToken(username: String, expireAfter: Long): String {
        return JWT.create()
            .withSubject(username)
            .withExpiresAt(Date(System.currentTimeMillis() + expireAfter))
            .sign(algorithm)
    }

    private fun generateToken(username: String, expireAfter: Long, role: UserRole): String {
        return JWT.create()
            .withSubject(username)
            .withClaim("role", role.name)
            .withExpiresAt(Date(System.currentTimeMillis() + expireAfter))
            .sign(algorithm)
    }

    fun verify(token: String): Boolean {
        return try {
            val verify = verifier.verify(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun decode(token: String): DecodedJWT? {
        return try {
            JWT.decode(token)
        } catch (e: Exception) {
            null
        }
    }

}