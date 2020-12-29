package com.example.myapplication.model

import kotlin.system.exitProcess

interface IAuthorization {
    fun loginCheck(login: String): Boolean
    fun passwordCheck(password: String): Boolean
    fun authorization(login: String, password: String)
}

data class User(
        val id: Int,
        val name: String,
        val login: String,
        val password: String,
        val phone: String
)

class AuthorizationChecker(private val auth: IAuthorization) {
    var users = listOf(
            User(id = 0, name = "Andrey", login = "and1203", password = "***pswrd***", phone = "+7920938291"),
            User(id = 1, name = "Alisa", login = "alise101", password = "***prsdd***", phone = "+7910933241"),
            User(id = 1, name = "Pavel", login = "pvl101", password = "pvl111PVL", phone = "+7915931281")
    )

    fun check(login: String, password: String) {
        if (auth.loginCheck(login) && (auth.passwordCheck(password))) {
            users.forEach {
                if (it.login.contentEquals(login) && it.password.contentEquals(password)) {
                    auth.authorization(login, password)
                    return@forEach
                }
            }
        } else throw Exception("That login and password is not correct!")
    }
}