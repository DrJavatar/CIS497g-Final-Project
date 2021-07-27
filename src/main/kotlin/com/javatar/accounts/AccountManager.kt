package com.javatar.accounts

import com.javatar.data.LoginRecord

class AccountManager {

    val accounts = mutableMapOf<String, UserAccount>()

    val activeSessions = mutableMapOf<String, String>()

    val loginRecords = mutableListOf<LoginRecord>()

    fun register(account: UserAccount) {
        accounts[account.name] = account
    }

    fun isAdmin(user: String) : Boolean = accounts[user]?.privilege === Privilege.ADMIN

}