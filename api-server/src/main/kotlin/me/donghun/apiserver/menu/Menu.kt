package me.donghun.apiserver.menu

import me.donghun.apiserver.account.UserRole
import javax.persistence.*

@Entity
class Menu (
    @Id
    @GeneratedValue
    var id: Long? = null,
    var name: String,
    var price: Integer,
)