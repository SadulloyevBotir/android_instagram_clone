package com.example.android_instagram_clone.manager.handler

import com.example.android_instagram_clone.model.User
import java.lang.Exception

interface DBUsersHandler {
    fun onSuccess(users: ArrayList<User>)
    fun onError(e: Exception)
}