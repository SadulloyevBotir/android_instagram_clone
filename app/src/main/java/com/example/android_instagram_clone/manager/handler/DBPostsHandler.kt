package com.example.android_instagram_clone.manager.handler

import com.example.android_instagram_clone.model.Post
import java.lang.Error
import java.lang.Exception

interface DBPostsHandler {
    fun onSuccess(posts: ArrayList<Post>)
    fun onError(e: Exception)
}