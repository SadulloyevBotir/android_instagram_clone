package com.example.android_instagram_clone.model

import java.text.SimpleDateFormat
import java.util.*

class Post {
    var id: String = ""
    var caption: String = ""
    var postImg: String = ""
    var currentDate: String = currentTime()

    var uid: String = ""
    var fullname: String = ""
    var userImg: String = ""
    var device_token = ""

    var isLiked :Boolean= false

    constructor(caption: String, postImg: String) {
        this.postImg = postImg
        this.caption = caption
    }

    constructor(id: String, caption: String, postImg: String) {
        this.id = id
        this.postImg = postImg
        this.caption = caption
    }

    private fun currentTime(): String {
        val sdf = SimpleDateFormat("dd/M/yyy hh:mm")
        return sdf.format(Date())
    }
}