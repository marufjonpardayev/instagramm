package com.example.instagramm.manager.handler

import java.lang.Exception

interface StorageHandler {
    fun onSuccess(imgUrl: String)
    fun onError(exception: Exception?)
}