package com.javatar

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

object WebServer {


    @JvmStatic
    fun main(args: Array<String>) {

        embeddedServer(Netty, 4500) {

            routing {

                route("/hello") {

                    get {
                        println("Fucking ay!")
                        call.respond("Hello, World")
                    }

                }

            }

        }.start(wait = true)

    }


}