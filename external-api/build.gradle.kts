dependencies {
	implementation(project(":core"))

	implementation(libs.aws.s3) {
		exclude("com.squareup.okhttp3:okhttp")
	}
	implementation(libs.aws.ses) {
		exclude("com.squareup.okhttp3:okhttp")
	}

	implementation(libs.springboot.mail)
	implementation(libs.springboot.thymeleaf)
	implementation(libs.springboot.web)
	implementation(libs.springboot.web)
}
