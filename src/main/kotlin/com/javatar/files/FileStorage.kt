package com.javatar.files

import com.google.gson.GsonBuilder
import com.javatar.accounts.AccountManager
import java.nio.file.Files
import java.nio.file.Path

class FileStorage {

    val gson = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()

    fun saveAccounts(manager: AccountManager) {
        Files.write(Path.of("accounts.json"), gson.toJson(manager).toByteArray())
    }

    fun loadAccounts(): AccountManager {
        return try {
            gson.fromJson(
                Files.readString(Path.of("accounts.json")),
                AccountManager::class.java
            )
        } catch (e: Exception) {
            return AccountManager()
        }
    }

}