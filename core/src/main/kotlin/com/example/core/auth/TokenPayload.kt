package com.example.core.auth

import com.example.core.enums.Role
import java.util.UUID

data class TokenPayload(
    val userId: UUID,
    val role: Role,
)
