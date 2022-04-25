package com.example.android_instagram_clone.model


data class FCMNoti(var notification: Notification, var registration_ids: ArrayList<String>)

data class Notification(var title: String, var body: String)