package com.example.instagramm.manager.handler

import java.lang.Exception

interface DBFollowHandler {
    fun onSuccess(isDone: Boolean)
    fun onError(e: Exception)
}
