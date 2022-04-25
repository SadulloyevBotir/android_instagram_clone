package com.example.android_instagram_clone.manager.handler

interface StorageHandler {
    fun onSuccess(imgUrl: String)
    fun onError(exception: Exception?)
}