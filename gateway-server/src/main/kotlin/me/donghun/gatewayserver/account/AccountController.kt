package me.donghun.gatewayserver.account

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import me.donghun.gatewayserver.TokenManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class AccountController @Autowired constructor(val accountRepository: AccountRepository) {

    @PostMapping("/signin")
    fun signin(account: Account): ResponseEntity<Any> {
        val findByUsername = accountRepository.findByUsername(account.username)

        // TODO validation
        if(findByUsername != null) {
            return ResponseEntity.badRequest().body("username '${account.username}' already exists")
        }

        // TODO password encryption
        accountRepository.save(account)

        val accessToken = TokenManager.generateAccessToken(account.username)
        val refreshToken = TokenManager.generateRefreshToken(account.username)

        return ResponseEntity.ok().body(
            ObjectMapper().writeValueAsString(
                hashMapOf("access-token" to accessToken, "refresh-token" to refreshToken)
            )
        )
    }



}