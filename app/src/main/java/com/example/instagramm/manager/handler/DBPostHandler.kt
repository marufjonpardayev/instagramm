package com.example.instagramm.manager.handler


import com.example.instagramm.model.Post
import java.lang.Exception

interface DBPostHandler {
    fun onSuccess(post: Post)
    fun onError(e: Exception)
}