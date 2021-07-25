package com.javatar

import at.favre.lib.crypto.bcrypt.BCrypt
import com.javatar.accounts.AccountManager
import com.javatar.accounts.Privilege
import com.javatar.accounts.UserAccount
import com.javatar.files.FileStorage
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

object WebServer {

    val storage = FileStorage()

    @JvmStatic
    fun main(args: Array<String>) {

        val manager = storage.loadAccounts()

        if(manager.accounts.isEmpty()) {
            println("Generating Admin Account!")
            generateFirstTimeAccounts(manager, storage)
        }

        embeddedServer(Netty, 4501) {

            install(Authentication) {
                basic("admin") {
                    realm = "cis497g"
                    validate { creds ->
                        UserIdPrincipal(creds.name)
                    }
                }
            }

            routing {

                authenticate("admin") {
                    route("admin") {
                        get {
                            call.respond("This is an admin page.")
                        }
                    }
                }

            }

        }.start(wait = true)

    }

    private fun generateFirstTimeAccounts(manager: AccountManager, storage: FileStorage) {
        val name = "admin"
        val pass = "this is not the pass...."
        val hashedPass = BCrypt.withDefaults().hashToString(12, pass.toCharArray())
        val admin = UserAccount(name, hashedPass, Privilege.ADMIN)
        manager.register(admin)
        storage.saveAccounts(manager)
    }

}