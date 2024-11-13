package kr.rhakdnj.admin.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan(basePackages = ["kr.rhakdnj"])
@SpringBootApplication(scanBasePackages = ["kr.rhakdnj"])
class AdminApiApplication

fun main(args: Array<String>) {
	runApplication<AdminApiApplication>(*args)
}
