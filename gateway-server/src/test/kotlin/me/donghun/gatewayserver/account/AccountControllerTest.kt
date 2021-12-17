package me.donghun.gatewayserver.account

import com.fasterxml.jackson.databind.ObjectMapper
import me.donghun.gatewayserver.TokenManager
import me.donghun.gatewayserver.UserRole
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import wiremock.org.eclipse.jetty.util.ajax.JSON

@SpringBootTest
@AutoConfigureWebTestClient
internal class AccountControllerTest @Autowired constructor(private val webTestClient: WebTestClient,
                                                            private val accountRepository: AccountRepository) {

    @DisplayName("Sign-in")
    @Test
    fun signin() {
        val account = Account(username = "username1", password = "password1", roles = listOf(UserRole.NORMAL))

        val result = webTestClient.post()
            .uri("/signin")
            .body(
                BodyInserters.fromFormData("username", account.username)
                    .with("password", account.password)
            )
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.access-token").exists()
            .jsonPath("$.refresh-token").exists()
            .returnResult()

        val json = JSON.parse(String(result.responseBody!!)) as Map<String, String>
        assertThat(TokenManager.decode(json["access-token"]!!)?.subject).isEqualTo(account.username)
        assertThat(TokenManager.decode(json["refresh-token"]!!)?.subject).isEqualTo(account.username)
        assertThat(accountRepository.findByUsername(account.username)).isNotNull
    }

}