package me.donghun.apiserver.account

import org.apache.commons.codec.digest.DigestUtils
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@org.springframework.transaction.annotation.Transactional
class AccountControllerTest @Autowired constructor(private val mockMvc: MockMvc,
                                                            private val accountRepository: AccountRepository) {

    @DisplayName("Sign-in")
    @Test
    fun signin() {
        val plainPassword = "password1"
        val account = Account(username = "username1", password = plainPassword, role = UserRole.USER)
        account.password = DigestUtils.sha256Hex(account.password)
        accountRepository.save(account)

        mockMvc.perform(post("/signin")
            .param("username", account.username)
            .param("password", plainPassword))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.access-token").exists())
                .andExpect(jsonPath("$.refresh-token").exists())
                .andDo(print())
    }

    @DisplayName("Sign-in failure")
    @Test
    fun signinFailure() {
        val account = Account(username = "username1", password = "password1", role = UserRole.USER)

        mockMvc.perform(post("/signin")
            .param("username", account.username)
            .param("password", account.password))
            .andExpect(status().isUnauthorized)
            .andDo(print())
    }

    @DisplayName("Sign-up")
    @Test
    fun signUp() {
        val account = Account(username = "username1", password = "password1", role = UserRole.USER)

        mockMvc.perform(post("/signup")
            .param("username", account.username)
            .param("password", account.password))
            .andExpect(status().isOk)
            .andDo(print())

        assertThat(accountRepository.findByUsername(account.username)).isNotNull
    }

    @DisplayName("Sign-up failure - duplicated id")
    @Test
    fun signUpFailure_duplicatedId() {
        val account = Account(username = "username1", password = "password1", role = UserRole.USER)
        accountRepository.save(account);

        mockMvc.perform(post("/signup")
            .param("username", account.username)
            .param("password", account.password))
            .andExpect(status().isBadRequest)
            .andDo(print())
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