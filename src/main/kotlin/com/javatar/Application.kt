package com.javatar

import at.favre.lib.crypto.bcrypt.BCrypt
import com.javatar.accounts.AccountManager
import com.javatar.accounts.Privilege
import com.javatar.accounts.UserAccount
import com.javatar.accounts.UserSession
import com.javatar.data.LoginRecord
import com.javatar.data.TaxBaseValues
import com.javatar.files.FileStorage
import io.ktor.application.*
import io.ktor.application.Application
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.html.*
import java.io.File
import java.time.LocalDateTime
import java.util.*

object Application {

    private val storage = FileStorage()
    private var taxValues = TaxBaseValues()

    fun Application.main() {

        val manager = storage.loadAccounts()
        taxValues = storage.loadTaxBaseValues()

        if (manager.accounts.isEmpty()) {
            println("Generating Admin Account!")
            generateFirstTimeAccounts(manager, storage)
        }

        install(ContentNegotiation) {
            gson()
        }

        install(Sessions) {
            cookie<UserSession>("user_session")
        }

        install(Authentication) {
            session<UserSession>("auth-session") {
                validate {
                    println("Validating ${it.id} - ${manager.activeSessions.containsKey(it.id)}")
                    if (manager.activeSessions.containsKey(it.id)) {
                        return@validate it
                    } else {
                        return@validate null
                    }
                }
                challenge {
                    call.respond(UnauthorizedResponse())
                }
            }
            form("admin") {
                userParamName = "usrname"
                passwordParamName = "password"
                validate { creds ->
                    println("User ${creds.name} - ${creds.password}")
                    if (manager.accounts.containsKey(creds.name)) {
                        val user = manager.accounts[creds.name]!!
                        if (BCrypt.verifyer().verify(creds.password.toCharArray(), user.password).verified) {
                            return@validate UserIdPrincipal(creds.name)
                        } else {
                            return@validate null
                        }
                    } else {
                        return@validate null
                    }
                }
            }
        }

        routing {

            try {
                get("test") {
                    call.respond("Hello, World")
                }

                get("values") {
                    call.respond(taxValues)
                }

                static("/") {
                    staticRootFolder = File("login")
                    default("login.html")
                }
                static("assets") {
                    staticRootFolder = File("login")
                    files("assets")
                }

                authenticate("admin") {
                    post("login") {
                        val id = UUID.randomUUID().toString()
                        val user = call.principal<UserIdPrincipal>()
                        if (user != null) {
                            call.sessions.set(UserSession(id))
                            manager.activeSessions[id] = user.name
                            manager.activeSessions.forEach { (t, u) ->
                                println("User $u - ID $t")
                            }
                            manager.loginRecords.add(LoginRecord(user.name, LocalDateTime.now().toString()))
                            call.respondRedirect("/panel")
                        } else {
                            call.respond(UnauthorizedResponse())
                        }
                    }
                }

                authenticate("auth-session") {
                    static("/panel") {
                        staticRootFolder = File("login")
                        default("admin-page.html")
                        post {
                            try {
                                parseParameters(call.receiveParameters())
                                call.respondRedirect("/panel")
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        get("logs") {
                            call.respondHtml {
                                head {
                                    title {
                                        +"Login Logs"
                                    }
                                }
                                body {
                                    table {
                                        thead {
                                            tr {
                                                th {
                                                    +"Username"
                                                }
                                                th {
                                                    +"Date"
                                                }
                                            }
                                        }
                                        tbody {
                                            manager.loginRecords.forEach {
                                                tr {
                                                    td {
                                                        +it.username
                                                    }
                                                    td {
                                                        +it.date
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        get("/logout") {
                            call.sessions.clear<UserSession>()
                            call.respondRedirect("/login")
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun parseParameters(params: Parameters) {
        val bcBracket = params["BCBracket"]?.split(",")?.map { it.toInt() }?.toIntArray() ?: intArrayOf()
        val bcRate = params["BCRate"]?.split(",")?.map { it.toDouble() }?.toDoubleArray() ?: doubleArrayOf()
        val federalBracket = params["username"]?.split(",")?.map { it.toInt() }?.toIntArray() ?: intArrayOf()
        val federalRate = params["federalRate"]?.split(",")?.map { it.toDouble() }?.toDoubleArray() ?: doubleArrayOf()
        val tb = TaxBaseValues(bcBracket, bcRate, federalBracket, federalRate)
        storage.saveTaxBaseValues(tb)
        taxValues = tb
    }

    private fun generateFirstTimeAccounts(manager: AccountManager, storage: FileStorage) {
        val name = "admin"
        val pass = "cis497g"
        val hashedPass = BCrypt.withDefaults().hashToString(12, pass.toCharArray())
        val admin = UserAccount(name, hashedPass, Privilege.ADMIN)
        manager.register(admin)
        storage.saveAccounts(manager)
    }
}