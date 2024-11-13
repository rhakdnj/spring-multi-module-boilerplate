package kr.rhakdnj.core.redis.repository

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisKeyValueRepository(
	private val redisTemplate: StringRedisTemplate,
) {
	fun save(
		key: String,
		value: String,
		expireSeconds: Long,
	) {
		redisTemplate
			.opsForValue()
			.set(key, value, expireSeconds, TimeUnit.SECONDS)
	}

	fun find(key: String): String? = redisTemplate.opsForValue().get(key)

	fun delete(key: String) {
		redisTemplate.delete(key)
	}
}
