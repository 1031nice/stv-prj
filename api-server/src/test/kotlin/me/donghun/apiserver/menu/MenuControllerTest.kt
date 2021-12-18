package me.donghun.apiserver.menu

import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@org.springframework.transaction.annotation.Transactional
class MenuControllerTest @Autowired constructor(private val mockMvc: MockMvc,
                                                private val menuRepository: MenuRepository) {

    @DisplayName("Get menus")
    @Test
    fun getMenus() {
        menuRepository.save(Menu(name = "MENU1", price = 1000))
        menuRepository.save(Menu(name = "MENU2", price = 2000))

        /*
        TODO 테스트 DB H2, 운영 DB postgreSQL로 분리한 뒤 제대로 테스트
         */
        mockMvc.perform(get("/menus"))
            .andExpect(status().isOk)
            .andDo(print())
    }

    @DisplayName("Add menu")
    @Test
    fun addMenu() {
        mockMvc.perform(post("/menus")
            .param("name", "탕수육")
            .param("price", "15000"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name", `is`("탕수육")))
                .andDo(print())

        assertThat(menuRepository.findByName("탕수육")).isNotNull
    }

    @DisplayName("Add menu failure - duplicated name")
    @Test
    fun addMenuFailure_duplicatedName() {
        menuRepository.save(Menu(name = "MENU", price = 10000))

        mockMvc.perform(post("/menus")
            .param("name", "MENU")
            .param("price", "10000"))
            .andExpect(status().isBadRequest)
            .andDo(print())
    }

}