plugins {
    kotlin("jvm") version "1.9.23"
}

group = "price.dustin.swingkt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}


dependencies {
    implementation("com.formdev:flatlaf:3.5.4")
    testImplementation(kotlin("test"))

    implementation("com.github.JetBrains.jediterm:jediterm-core:ecbb72ad28")
    implementation("com.github.JetBrains.jediterm:jediterm-ui:ecbb72ad28")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
