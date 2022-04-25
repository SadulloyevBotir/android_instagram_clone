package com.example.android_instagram_clone.manager.handler

import com.example.android_instagram_clone.model.Post
import java.lang.Error
import java.lang.Exception

interface DBPostHandler {
    fun onSuccess(post: Post)
    fun onError(e: Exception)
}