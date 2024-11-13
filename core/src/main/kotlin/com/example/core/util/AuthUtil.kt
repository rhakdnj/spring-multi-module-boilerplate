package com.example.core.util

import com.example.core.enums.Role
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User

fun findUserIdFromSecurityContextHolder(): String? {
    val principal = SecurityContextHolder.getContext().authentication?.principal as? User
    return principal?.username
}

fun findRolesFromSecurityContextHolder(): Role? {
    val principal = SecurityContextHolder.getContext().authentication?.principal as? User
    return principal?.authorities?.firstOrNull()?.let { Role.from(it.authority) }
}
