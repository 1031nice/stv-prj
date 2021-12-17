package me.donghun.apiserver.account

import com.fasterxml.jackson.databind.ObjectMapper
import me.donghun.apiserver.TokenManager
import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
internal class AccountControllerTest @Autowired constructor(private val mockMvc: MockMvc,
                                                            private val accountRepository: AccountRepository) {

    @DisplayName("Sign-in")
    @Test
    fun signin() {
        val account = Account(username = "username1", password = "password1", roles = listOf(UserRole.NORMAL))

        mockMvc.perform(post("/signin")
            .param("username", account.username)
            .param("password", account.password))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.access-token").exists())
                .andExpect(jsonPath("$.refresh-token").exists())
                .andDo(print())

        assertThat(accountRepository.findByUsername(account.username)).isNotNull
    }

    @DisplayName("Sample API")
    @Test
    fun hello() {
        mockMvc.perform(get("/hello"))
            .andExpect(status().isOk)
            .andExpect(content().string("Hello World"))
            .andDo(print())
    }

}