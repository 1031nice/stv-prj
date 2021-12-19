package me.donghun.apiserver.account

import com.fasterxml.jackson.databind.ObjectMapper
import me.donghun.apiserver.TokenManager
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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
        val findByUsername = accountRepository.findByUsername(account.username) ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        if (!DigestUtils.sha256Hex(account.password).equals(findByUsername.password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val accessToken = TokenManager.generateAccessToken(account.username, findByUsername.role!!)
        val refreshToken = TokenManager.generateRefreshToken(account.username, findByUsername.role!!)

        return ResponseEntity.ok().body(
            ObjectMapper().writeValueAsString(
                hashMapOf("access-token" to accessToken, "refresh-token" to refreshToken)
            )
        )
    }

    @PostMapping("/signup")
    fun signup(account: Account): ResponseEntity<Any> {
        val findByUsername = accountRepository.findByUsername(account.username)
        if(findByUsername != null) return ResponseEntity.badRequest().build()

        account.password = DigestUtils.sha256Hex(account.password);
        accountRepository.save(account)

        return ResponseEntity.ok().build()
    }

    @GetMapping("/hello")
    @ResponseBody
    fun hello(): String {
        return "Hello World"
    }

}