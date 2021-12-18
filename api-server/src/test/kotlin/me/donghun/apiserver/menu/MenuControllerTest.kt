package me.donghun.apiserver.menu

import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@org.springframework.transaction.annotation.Transactional
class MenuControllerTest @Autowired constructor(private val mockMvc: MockMvc,
                                                private val menuRepository: MenuRepository) {

    @DisplayName("Get menus")
    @Test
    fun getMenus() {
        menuRepository.save(Menu(name = "짜장면", price = 5000))
        menuRepository.save(Menu(name = "짬뽕", price = 6000))

        mockMvc.perform(get("/menus"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[*].name", containsString("짜장면")))
            .andExpect(jsonPath("$[*].name", containsString("짬뽕")))
            .andDo(print())
    }

}