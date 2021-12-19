package me.donghun.apiserver.account

import javax.persistence.*

@Entity
class Account(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var username: String,
    var password: String,
    @Enumerated(EnumType.STRING)
    var role: UserRole?
)