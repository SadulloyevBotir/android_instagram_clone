package com.example.android_instagram_clone.manager.handler

interface AuthHandler {
    fun onSuccess(uid: String)
    fun onError(exception: Exception?)
}