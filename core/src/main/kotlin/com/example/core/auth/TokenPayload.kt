package com.example.core.auth

import com.example.core.enums.Role

data class TokenPayload(
    val userId: Long,
    val role: Role,
)
