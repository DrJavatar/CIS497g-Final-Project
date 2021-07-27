package com.javatar.files

import com.google.gson.GsonBuilder
import com.javatar.accounts.AccountManager
import com.javatar.data.TaxBaseValues
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

    fun saveTaxBaseValues(base: TaxBaseValues) {
        Files.write(Path.of("taxValues.json"), gson.toJson(base).toByteArray())
    }

    fun loadTaxBaseValues(): TaxBaseValues {
        return try {
            gson.fromJson(
                Files.readString(Path.of("taxValues.json")),
                TaxBaseValues::class.java
            )
        } catch (e: java.lang.Exception) {
            return TaxBaseValues()
        }
    }

}