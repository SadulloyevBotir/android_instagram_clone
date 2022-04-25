package com.example.android_instagram_clone.network

import com.example.android_instagram_clone.model.Post
import com.example.android_instagram_clone.network.service.NotiService
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHttp {
    public var IS_TESTER = true
    private val SERVER_DEVELOPMENT = "https://fcm.googleapis.com"
    private val SERVER_PRODUCTION = "https://fcm.googleapis.com"


    private var retrofit = Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create()).build()

    fun server(): String {
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }
    val notiService:NotiService = retrofit.create(NotiService::class.java)
}