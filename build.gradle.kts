plugins {
    kotlin("jvm") version "1.5.31"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val kotlinVersion = "1.5.31"
val spekVersion = "2.0.17"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("com.googlecode.lanterna:lanterna:3.1.1")

    testImplementation("org.assertj:assertj-core:3.21.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.0.0.RC")
}

tasks.withType<Test> {
    useJUnitPlatform()
}