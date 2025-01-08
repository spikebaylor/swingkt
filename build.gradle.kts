plugins {
    kotlin("jvm") version "1.9.23"
    `maven-publish`
}

group = "price.dustin.swingkt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = group.toString()
            artifactId = "swingkt"
            version = "1.0-SNAPSHOT"

            from(components["java"])
        }
    }
}