import io.gitlab.arturbosch.detekt.Detekt

group = "org.sopcastultras.seabattle3d"
version = "1.0-SNAPSHOT"

val kotestVersion = "5.0.1"
val exposedVersion = "0.36.1"

plugins {
    kotlin("jvm") version "1.5.32"
    id("io.qameta.allure") version "2.9.6"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
    application
}

allure {
    adapter {
        autoconfigure.set(true)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.googlecode.lanterna:lanterna:3.1.1")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-extensions-allure:4.4.3")
    testImplementation("org.slf4j:slf4j-simple:1.7.32")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.19.0")
}

application {
    mainClass.set("MainKt")
}

tasks.withType<Test> {
    useJUnitPlatform()
    ignoreFailures = true
    finalizedBy(tasks.allureReport, tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

detekt {
    buildUponDefaultConfig = true
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        sarif.required.set(true)
    }
}
