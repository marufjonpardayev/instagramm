package com.example.instagramm.manager.handler

import com.example.instagramm.model.User

interface DBUserHandler {
    fun onSuccess(user: User?=null)
    fun onError(e:Exception)
}