package com.example.android_instagram_clone.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.view.WindowManager
import com.example.android_instagram_clone.R
import com.example.android_instagram_clone.manager.AuthManager
import com.example.android_instagram_clone.manager.PrefsManager
import com.example.android_instagram_clone.utils.Logger
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

/**
 * In SplashActivity , user can visit to SignInActivity or MainActivity
 */
class SplashActivity : BaseActivity() {
    val TAG = SplashActivity::class.java.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
        setContentView(R.layout.activity_splash)
        initViews()

    }


    private fun initViews() {
        countDownTimer()
        loadFCMToken()
    }

    private fun countDownTimer() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if (AuthManager.isSignIn()) {
                    callMainActivity(this@SplashActivity)
                } else {
                    callSignInActivity(this@SplashActivity)
                }
            }
        }.start()
    }


    private fun loadFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Logger.d(TAG, "Fetching FCM registration token failed")
                return@OnCompleteListener
            }
            // Get new FCM registration token
            // Save it in locally to use later
            val token = task.result
            Logger.d(TAG, token.toString())
            PrefsManager(this).storeDeviceToken(token.toString())
        })
    }
}