plugins {
    kotlin("jvm") version "1.5.21"
}

group = "com.javatar"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-core:1.6.1")
    implementation("io.ktor:ktor-server-netty:1.6.1")
    implementation("io.ktor:ktor-auth:1.5.2")
    implementation("io.ktor:ktor-gson:1.5.2")
    implementation("io.ktor:ktor-network-tls-certificates:1.5.2")
    implementation("at.favre.lib:bcrypt:0.9.0")
}
