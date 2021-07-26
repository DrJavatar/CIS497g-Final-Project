package com.javatar.accounts

class AccountManager {

    val accounts = mutableMapOf<String, UserAccount>()

    val activeSessions = mutableMapOf<String, String>()

    fun register(account: UserAccount) {
        accounts[account.name] = account
    }

    fun isAdmin(user: String) : Boolean = accounts[user]?.privilege === Privilege.ADMIN

}