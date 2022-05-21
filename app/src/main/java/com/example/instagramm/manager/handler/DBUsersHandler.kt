package com.example.instagramm.manager.handler

import com.example.instagramm.model.User
import java.lang.Exception

interface DBUsersHandler {
    fun onSuccess(users: ArrayList<User>)
    fun onError(e: Exception)
}