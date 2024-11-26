package com.example.api.configuration.local

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("local")
class LocalApplicationRunner(
    private val userRunner: LocalUserRunner,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        userRunner.init()
    }
}
