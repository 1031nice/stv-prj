package me.donghun.apiserver.menu

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/menus")
@Controller
class MenuController @Autowired constructor(private val menuRepository: MenuRepository) {

    @GetMapping
    fun getMenus(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(menuRepository.findAll())
    }

    @PostMapping
    fun createMenu(): ResponseEntity<Any> {
        return ResponseEntity.badRequest().build()
    }

}