plugins {
    id("java-library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    // JUnit test
    testImplementation(libs.junit)

    // Xposed Api
    compileOnly(libs.xposed.api)
}