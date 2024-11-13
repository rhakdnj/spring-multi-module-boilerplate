package kr.rhakdnj.core.redis.repository

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenRepository(
	private val redisTemplate: StringRedisTemplate,
) {
	fun findByUserId(userId: Long): String? = redisTemplate.opsForValue().get(userId.toString())

	fun save(
		userId: Long,
		refreshToken: String,
		refreshTokenTimeoutMs: Long,
	) {
		redisTemplate.opsForValue().set(userId.toString(), refreshToken, refreshTokenTimeoutMs, TimeUnit.MILLISECONDS)
	}
}
