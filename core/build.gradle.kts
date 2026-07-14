plugins {
    id("com.android.library")
}

android {
    namespace = "com.yukino.androidpatcher.core"
    compileSdk = 37

    defaultConfig {
        minSdk = 24
        // No applicationId
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    // Android
    implementation(libs.androidx.annotation.jvm)

    // JUnit test
    testImplementation(libs.junit)

    // Xposed Api
    compileOnly(libs.xposed.api)
}