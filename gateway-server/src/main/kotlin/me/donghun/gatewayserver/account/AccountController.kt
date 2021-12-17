package me.donghun.gatewayserver.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController @Autowired constructor(val accountRepository: AccountRepository) {

    @PostMapping("/signin")
    fun signin(account: Account): ResponseEntity<Any> {
        val findByUsername = accountRepository.findByUsername(account.username)

        // TODO validation
        if(findByUsername != null) {
            return ResponseEntity.badRequest().body("username '${account.username}' already exists")
        }

        accountRepository.save(account)
        // TODO generate token
        return ResponseEntity.ok()
            .header("access-token", "access-token")
            .header("refresh-token", "refresh-token")
            .build()
    }

}