package com.example.android_instagram_clone.manager

import android.content.Context
import android.content.res.Configuration
import android.graphics.*
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RectShape
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.graphics.green
import com.example.android_instagram_clone.R
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class FirebaseConfig(var ll: LinearLayout, var tv: TextView) {
    var remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
            fetchTimeoutInSeconds = 60
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    fun applyConfig() {
        val color1 = remoteConfig.getString("color1")
        val color2 = remoteConfig.getString("color2")
        val fontSize = remoteConfig.getString("welcome_font_size")
        val text = remoteConfig.getString("welcome_text")


        Log.d("@@@", color1)
        Log.d("@@@", color2)
        Log.d("@@@", fontSize)
        Log.d("@@@", text)

        this.ll.backgroundGradientDrawable(Color.parseColor(color1), Color.parseColor(color2))
        this.tv.text = text
        this.tv.textSize = fontSize.toFloat()

        if(isTablet(ll.context)){
            this.ll.setBackgroundColor(Color.parseColor(color1))
        }else{
            this.ll.setBackgroundColor(Color.parseColor(color2))
        }
    }

    fun isTablet(context: Context): Boolean {
        val xlarge = context.resources
            .configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK === 4
        val large = context.resources
            .configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK === Configuration.SCREENLAYOUT_SIZE_LARGE
        return xlarge || large
    }


    fun View.backgroundGradientDrawable(@ColorInt startColor: Int, @ColorInt endColor: Int): Unit {
        val h = this.height.toFloat()
        val shapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.paint.shader =
            LinearGradient(0f, 0f, 0f, h, startColor, endColor, Shader.TileMode.REPEAT)
        this.background = shapeDrawable
    }

    fun updateConfig() {
        remoteConfig.fetch(0).addOnCompleteListener {
            if (it.isSuccessful) {
                remoteConfig.activate()
                applyConfig()
            } else {
                Log.d("@@@", "Fetch failed")
            }
        }
    }
}