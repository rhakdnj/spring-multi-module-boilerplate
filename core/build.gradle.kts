allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    implementation(libs.springboot.web)

    implementation(libs.springboot.data.jpa)
    implementation("com.querydsl:querydsl-jpa:${rootProject.libs.versions.querydsl.get()}:jakarta")
    kapt("com.querydsl:querydsl-apt:${rootProject.libs.versions.querydsl.get()}:jakarta")
    implementation(libs.springboot.redis)

    implementation(libs.bundles.jwt)

    implementation(libs.springboot.actuator)
    implementation(libs.prometheus)
    compileOnly(libs.springboot.security)
    compileOnly(libs.springboot.data.jpa)

    api(libs.logging)

    runtimeOnly(libs.postgresql)
}
