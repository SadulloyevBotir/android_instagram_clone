package com.example.android_instagram_clone.network.service

import com.example.android_instagram_clone.model.FCMNoti
import com.example.android_instagram_clone.model.FCMRest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotiService {
    companion object {
        private const val SERVER_KEY =
            "AAAAdRelK7o:APA91bFE6iJEoSMI5MEqxm07monTi8faAipxMAIMurBq8QaM8BXKnWw2jcIbf9mq95_x_HxYTLw3FEczhUcSEmNh7JnDnL1PJ2Bfc522zOx4qM40mrL-VWzR4r77OezPFCbhAumxycaG"
    }

    @Headers("Authorization:key=$SERVER_KEY")
    @POST("/fcm/send")
    fun sendNoti(@Body FCMNote:FCMNoti): Call<FCMRest>
}