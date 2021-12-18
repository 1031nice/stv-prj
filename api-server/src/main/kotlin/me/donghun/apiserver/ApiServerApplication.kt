package me.donghun.apiserver

import me.donghun.apiserver.account.Account
import me.donghun.apiserver.account.AccountRepository
import me.donghun.apiserver.account.UserRole
import me.donghun.apiserver.menu.Menu
import me.donghun.apiserver.menu.MenuRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ApiServerApplication {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var menuRepository: MenuRepository

    @Bean
    fun runner(): ApplicationRunner {
        return ApplicationRunner {
            accountRepository.save(Account(null, "owner", "owner", listOf(UserRole.OWNER)))
            accountRepository.save(Account(null, "user", "user", listOf(UserRole.USER)))

            menuRepository.save(Menu(null, "짜장면", 5000))
            menuRepository.save(Menu(null, "짬뽕", 6000))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ApiServerApplication>(*args)
}