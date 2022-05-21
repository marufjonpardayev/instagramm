package com.example.instagramm.manager.handler

import com.example.instagramm.model.Post
import java.lang.Exception

interface DBPostsHandler {
    fun onSuccess(posts: ArrayList<Post>)
    fun onError(e: Exception)
}