package me.donghun.gatewayserver.account

import me.donghun.gatewayserver.UserRole
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@SpringBootTest
@AutoConfigureWebTestClient
internal class AccountControllerTest @Autowired constructor(private val webTestClient: WebTestClient,
                                                            private val accountRepository: AccountRepository) {

    @DisplayName("Sign-in")
    @Test
    fun signin() {
        val account = Account(username = "username1", password = "password1", roles = listOf(UserRole.NORMAL))
        val accessToken = "access-token"
        val refreshToken = "refresh-token"

        webTestClient.post()
            .uri("/signin")
            .body(BodyInserters.fromFormData("username", account.username)
                .with("password", account.password))
            .exchange()
            .expectStatus().isOk
            .expectHeader().valueEquals("access-token", "access-token")
            .expectHeader().valueEquals("refresh-token", "refresh-token")

        assertThat(accountRepository.findByUsername(account.username)).isNotNull
    }

}