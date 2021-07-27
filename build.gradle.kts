plugins {
    kotlin("jvm") version "1.5.21"
    id("org.beryx.runtime") version "1.12.5"
    application
}

group = "com.javatar"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.javatar.WebServer")
}

runtime {
    modules.set(listOf("java.sql", "java.desktop", "jdk.unsupported", "java.scripting", "java.logging", "java.xml", "java.naming"))
    options.set(listOf("--compress", "2", "--no-header-files", "--no-man-pages"))
    targetPlatform("linux", "/usr/lib/jvm/java-16-jdk")
}


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
    implementation("io.ktor:ktor-html-builder:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")
    implementation("at.favre.lib:bcrypt:0.9.0")
}
