package com.javatar.accounts

class AccountManager {

    val accounts = mutableMapOf<String, UserAccount>()

    fun register(account: UserAccount) {
        accounts[account.name] = account
    }

    fun isAdmin(user: String) : Boolean = accounts[user]?.privilege === Privilege.ADMIN

}