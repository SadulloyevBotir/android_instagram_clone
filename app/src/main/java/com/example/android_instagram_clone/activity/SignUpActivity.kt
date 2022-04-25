package com.example.android_instagram_clone.activity

/**
 * In  SignUpActivity, user can signUp using fullname,email,password
 */
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.android_firebase_demo.utils.Extensions.toast
import com.example.android_instagram_clone.R
import com.example.android_instagram_clone.manager.AuthManager
import com.example.android_instagram_clone.manager.DatabaseManager
import com.example.android_instagram_clone.manager.PrefsManager
import com.example.android_instagram_clone.manager.handler.AuthHandler
import com.example.android_instagram_clone.manager.handler.DBUserHandler
import com.example.android_instagram_clone.model.User
import com.example.android_instagram_clone.utils.Logger
import com.example.android_instagram_clone.utils.Utils

class SignUpActivity : BaseActivity() {
    val TAG = SignUpActivity::class.java.toString()
    lateinit var et_fullname: EditText
    lateinit var et_password: EditText
    lateinit var et_cpassword: EditText
    lateinit var et_email: EditText
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        initViews()
    }

    fun initViews() {
        context = this

        et_fullname = findViewById(R.id.et_fullname)
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        et_cpassword = findViewById(R.id.et_cpassword)

        val b_signup = findViewById<Button>(R.id.b_signup)
        b_signup.setOnClickListener {
            val fullname = et_fullname.text.toString().trim()
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            if (fullname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val user = User(fullname, email, password, "")
                firebaseSignUp(user)
            }
        }
        val tv_signin = findViewById<TextView>(R.id.tv_signin)
        tv_signin.setOnClickListener {
            callSignInActivity(this)
        }
    }

    private fun firebaseSignUp(user: User) {
        showLoading(this)
        AuthManager.signUp(user.email, user.password, object : AuthHandler {
            override fun onSuccess(uid: String) {
                user.uid = uid
                storeUserToDB(user)
                dismissLoading()
                toast(getString(R.string.str_signup_success))
            }

            override fun onError(exception: Exception?) {
                Logger.i(TAG, exception!!.localizedMessage.toString())
                dismissLoading()
                toast(getString(R.string.str_signup_failed))
            }

        })
    }

    private fun storeUserToDB(user: User) {
        user.device_token = PrefsManager(this).loadDeviceToken()!!
        user.device_id = Utils.getDeviceID(this)

        DatabaseManager.storeUser(user, object : DBUserHandler {
            override fun onSuccess(user: User?) {
                dismissLoading()
                callMainActivity(context)
            }

            override fun onError(e: java.lang.Exception) {

            }

        })
    }

}