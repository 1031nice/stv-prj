package me.donghun.apiserver

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TokenTest {

    private val username = "donghun"
    private val hour = 60 * 60 * 1000
    private val algorithm = Algorithm.HMAC256("super-secret")
    private lateinit var validToken: String
    private lateinit var expiredToken: String
    private lateinit var verifier: JWTVerifier

    @BeforeAll
    fun setUp() {
        validToken = JWT.create()
            .withSubject(username)
            .withExpiresAt(Date(System.currentTimeMillis() + hour))
            .sign(algorithm)

        expiredToken = JWT.create()
            .withSubject(username)
            .withExpiresAt(Date(System.currentTimeMillis() - 1000))
            .sign(algorithm)

        verifier = JWT.require(algorithm).build()
    }

    @DisplayName("verify token")
    @Test
    fun test() {
        var decodedJWT = verifier.verify(validToken)
        assertThat(decodedJWT.subject).isEqualTo(username)

        val notValidToken = validToken.substring(0, validToken.length-1)
        assertThrows<SignatureVerificationException> { decodedJWT = verifier.verify(notValidToken) }
        assertThrows<TokenExpiredException> { decodedJWT = verifier.verify(expiredToken) }
    }

}

