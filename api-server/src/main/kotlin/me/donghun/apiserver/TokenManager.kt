package me.donghun.apiserver

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.*

object TokenManager {

    private const val hour = 60 * 60 * 1000L
    private val algorithm = Algorithm.HMAC256("super-secret")
    private val verifier = JWT.require(algorithm).build()

    fun generateAccessToken(username: String): String {
        return generateToken(username, hour)
    }

    fun generateRefreshToken(username: String): String {
        return generateToken(username, 24 * 7 * hour)
    }

    private fun generateToken(username: String, expireAfter: Long): String {
        return JWT.create()
            .withSubject(username)
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