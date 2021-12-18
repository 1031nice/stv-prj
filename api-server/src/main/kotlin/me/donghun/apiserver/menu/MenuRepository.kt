package me.donghun.apiserver.menu

import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository: JpaRepository<Menu, Long> {
    fun findByName(name: String): Menu?
}