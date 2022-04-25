package com.example.android_instagram_clone.service


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.android_instagram_clone.R
import com.example.android_instagram_clone.utils.Logger
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

private const val CHANNEL_ID = "my_channel"

class FCMService : FirebaseMessagingService() {
    val TAG = FCMService::class.java.simpleName

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Logger.i(TAG, "Refreshed token :: $token")
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }
//        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle(message.data["title"])
//            .setContentText(message.data["body"])
//            .setSmallIcon(R.drawable.ic_instagram)
//            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_instagram))
//            .setStyle(
//                if (message.data["image"].isNullOrEmpty()) {
//                    NotificationCompat.BigTextStyle().bigText(message.data["body"])
//                        .setBigContentTitle(message.data["title"])
//                } else {
//                    NotificationCompat.BigPictureStyle().setSummaryText(message.data["body"])
//                        .bigPicture(getBitmapFromUrl(message.data["image"]))
//                }
//            )
//            .setAutoCancel(true)
//            .build()
//
//        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//            createNotificationChannel(manager)
//        val notificationId = Random.nextInt()
//        manager.notify(notificationId, notification)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createNotificationChannel(manager: NotificationManager) {
//        val channelName = "channelName"
//        val channel = NotificationChannel(
//            CHANNEL_ID, channelName,
//            NotificationManager.IMPORTANCE_HIGH
//        ).apply {
//            description = "My channel description"
//            enableLights(true)
//            lightColor = Color.BLUE
//        }
//        manager.createNotificationChannel(channel)
//    }
//
//    private fun getBitmapFromUrl(imageUrl: String?): Bitmap? {
//        return try {
//            val url = URL(imageUrl)
//            val connection = url.openConnection() as HttpURLConnection
//            connection.doInput = true
//            connection.connect()
//            val input: InputStream = connection.inputStream
//            BitmapFactory.decodeStream(input)
//        } catch (e: Exception) {
//            // TODO Auto-generated catch block
//            e.printStackTrace()
//            null
//        }
//    }
}