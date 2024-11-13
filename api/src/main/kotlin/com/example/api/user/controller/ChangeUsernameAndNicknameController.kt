package com.example.api.user.controller

import com.example.api.user.useCase.UserNicknameAndNameUpdateUseCase
import com.example.core.auth.LoginUser
import com.example.core.auth.TokenPayload
import com.example.core.auth.annotation.Authorized
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "사용자 API")
@RestController
class ChangeUsernameAndNicknameController(
    private val service: UserNicknameAndNameUpdateUseCase,
) {
    @Operation(
        summary = "비밀번호 변경",
        responses = [
            ApiResponse(responseCode = "200"),
            ApiResponse(responseCode = "409", description = "이미 사용중인 닉네임입니다."),
        ],
    )
    @Authorized
    @PutMapping("/users/info")
    fun change(
        @LoginUser tokenPayload: TokenPayload,
        @Valid @RequestBody request: ChangeUsernameAndNicknameRequest,
    ): ResponseEntity<Unit> {
        service.change(tokenPayload.userId, request.name, request.nickname)
        return ResponseEntity.ok().build()
    }
}

data class ChangeUsernameAndNicknameRequest(
    @field:NotBlank(message = "이름은 필수값입니다.")
    @field:Size(max = 10, message = "이름은 10자 이하로 입력해주세요.")
    val name: String,
    @field:NotBlank(message = "닉네임은 필수값입니다.")
    @field:Size(max = 10, message = "닉네임은 30자 이하로 입력해주세요.")
    val nickname: String,
)
