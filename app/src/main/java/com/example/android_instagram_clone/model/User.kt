package com.example.android_instagram_clone.model

class User {
    var fullname: String = ""
    var email: String = ""

    constructor(fullname: String, email: String) {
        this.email = email
        this.fullname = fullname
    }
}