import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.3.72"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.2")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:2.4.3")
    implementation(kotlin("stdlib"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.4.3") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test:3.4.3")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.3.2")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.3.2")
    testImplementation("com.ninja-squad:springmockk:2.0.3")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}