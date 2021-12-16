package me.donghun.apiserver

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class AccountController {

    @GetMapping("/hello")
    @ResponseBody
    fun hello(): String {
        return "Hello World"
    }

}