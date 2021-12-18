package me.donghun.apiserver

import me.donghun.apiserver.account.Account
import me.donghun.apiserver.account.AccountRepository
import me.donghun.apiserver.account.UserRole
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ApiServerApplication {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Bean
    fun runner(): ApplicationRunner {
        return ApplicationRunner {
            accountRepository.save(Account(null, "admin", "admin", listOf(UserRole.ADMIN)))
            accountRepository.save(Account(null, "user", "user", listOf(UserRole.NORMAL)))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ApiServerApplication>(*args)
}