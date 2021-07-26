package com.javatar.accounts

import io.ktor.auth.*

data class UserSession(val id: String) : Principal