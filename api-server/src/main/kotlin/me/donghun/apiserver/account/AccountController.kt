package me.donghun.apiserver.account

import com.fasterxml.jackson.databind.ObjectMapper
import me.donghun.apiserver.TokenManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class AccountController @Autowired constructor(val accountRepository: AccountRepository) {

    // TODO validation
    @PostMapping("/signin")
    fun signin(account: Account): ResponseEntity<Any> {
        val findByUsername = accountRepository.findByUsername(account.username) ?: return ResponseEntity.badRequest().build()

        val accessToken = TokenManager.generateAccessToken(account.username)
        val refreshToken = TokenManager.generateRefreshToken(account.username)

        return ResponseEntity.ok().body(
            ObjectMapper().writeValueAsString(
                hashMapOf("access-token" to accessToken, "refresh-token" to refreshToken)
            )
        )
    }

    @GetMapping("/hello")
    @ResponseBody
    fun hello(): String {
        return "Hello World"
    }

}