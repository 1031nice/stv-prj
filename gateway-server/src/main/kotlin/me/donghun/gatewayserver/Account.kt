package me.donghun.gatewayserver

import javax.persistence.*

@Entity
class Account(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var username: String,
    var password: String,
    @ElementCollection(targetClass = UserRole::class)
    @Enumerated(EnumType.STRING)
    var roles: List<UserRole>
)