package kr.rhakdnj.admin.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan(basePackages = ["kr.rhakdnj"])
@SpringBootApplication(scanBasePackages = ["kr.rhakdnj"])
class ApiApplication

fun main(args: Array<String>) {
	runApplication<kr.rhakdnj.admin.api.ApiApplication>(*args)
}
