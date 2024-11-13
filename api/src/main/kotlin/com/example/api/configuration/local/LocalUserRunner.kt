package com.example.api.configuration.local

import com.example.core.domain.user.User
import com.example.core.domain.user.repository.UserRepository
import com.example.core.enums.Role
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@Profile("local")
class LocalUserRunner(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun init() {
        val user = "user"
        val admin = "admin"

        val testUser =
            User(
                email = "$user@test.com",
                password = passwordEncoder.encode(user),
                name = user,
                nickname = user,
                birth = "19970711",
                role = Role.ROLE_USER,
            )

        val testAdminUser =
            User(
                email = "$admin@test.com",
                password = passwordEncoder.encode(admin),
                name = admin,
                nickname = admin,
                birth = "19970711",
                role = Role.ROLE_ADMIN,
            )

        userRepository.saveAll(listOf(testUser, testAdminUser))
    }
}
