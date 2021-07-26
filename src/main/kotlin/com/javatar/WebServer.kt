package com.javatar

import io.ktor.network.tls.certificates.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.io.File

fun main(args: Array<String>) {

    val file = File("ssl/temp.jks")
    if (!file.exists()) {
        file.parentFile.mkdirs()
        generateCertificate(file, keyAlias = "CIS", keyPassword = "sslcis497g", jksPassword = "sslcis497g")
    }

    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)

}

