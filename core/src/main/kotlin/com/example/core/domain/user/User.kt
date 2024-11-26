package com.example.core.domain.user

import com.example.core.domain.common.BaseEntity
import com.example.core.enums.Role
import com.fasterxml.uuid.Generators
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "user")
class User(
    @Id
    val id: UUID = Generators.timeBasedEpochRandomGenerator().generate(),
    @Column(name = "sort", nullable = false)
    var role: Role = Role.ROLE_USER,
    @Column(name = "email", nullable = false, length = 45, unique = true)
    val email: String,
    @Column(name = "password", nullable = false, length = 100)
    var password: String = "NO_PASSWORD",
    @Column(name = "name", nullable = false, length = 10)
    var name: String,
    @Column(name = "nickname", nullable = false, length = 30, unique = true)
    var nickname: String,
    @Column(name = "birth", nullable = false, length = 8)
    var birth: String,
) : BaseEntity() {
    fun isUser() = role == Role.ROLE_USER

    fun isAdmin() = role == Role.ROLE_ADMIN

    fun encodePassword(encodedPassword: String) {
        password = encodedPassword
    }

    fun changeName(newName: String) {
        require(newName.length <= 10) { "사용자 이름은 10자 이내만 가능합니다." }
        name = newName
    }

    fun changeNickname(newNickname: String) {
        require(newNickname.length <= 30) { "사용자 닉네임은 30자 이내만 가능합니다." }
        nickname = newNickname
    }
}
