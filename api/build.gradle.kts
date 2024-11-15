import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = true
jar.enabled = false

dependencies {
	implementation(project(":core"))
	implementation(project(":external-api"))

	implementation(libs.kotlin.coroutines)

	// notice: aws sdk is not compatible with okhttp3 4.9.0, so we need to 5.0.0 higher
	implementation(libs.okhttp3)

	implementation(libs.springboot.web)
	implementation(libs.springboot.data.jpa)
	implementation(libs.springboot.validation)
	implementation(libs.springboot.security)
	implementation(libs.bundles.jwt)
	implementation(libs.swagger)

	testImplementation(libs.springboot.test)
}
