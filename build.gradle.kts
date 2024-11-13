import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kapt)
	alias(libs.plugins.kotlin.plugin.spring) apply false
	alias(libs.plugins.kotlin.plugin.jpa) apply false
	alias(libs.plugins.springboot) apply false
	alias(libs.plugins.springboot.denpendency.management)
	alias(libs.plugins.asciidoctor) apply false
	alias(libs.plugins.ktlint) apply false
}

allprojects {
	group = "com.example"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.kapt")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.asciidoctor.jvm.convert")
	apply(plugin = "org.jlleitschuh.gradle.ktlint")

	dependencies {
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

		// kapt: kotlin annotation processor
		kapt("org.springframework.boot:spring-boot-configuration-processor")
		kapt("org.springframework.boot:spring-boot-autoconfigure-processor")
	}

	tasks.getByName("bootJar") {
		enabled = false
	}

	tasks.getByName("jar") {
		enabled = true
	}

	kotlin {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict")
		}
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			jvmTarget =
				rootProject.libs.versions.java
					.get()
		}
	}

	java.toolchain.languageVersion =
		JavaLanguageVersion.of(
			rootProject.libs.versions.java
				.get(),
		)
}
