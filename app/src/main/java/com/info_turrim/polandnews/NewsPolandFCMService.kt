package com.info_turrim.polandnews

import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import com.info_turrim.polandnews.common.data.NewsNotification
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.GsonBuilder


class NewsPolandFCMService : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "news_poland_channel_id"
        const val SILENT_CHANNEL_ID = "news_poland_silent_channel_id"
    }

    private val gson = GsonBuilder().disableHtmlEscaping().create()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        var showNotification = true

        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        val notification =
            gson.fromJson(
                gson.toJson(message.data)
                    .replace("\\", "")
                    .replace("\"{", "{")
                    .replace("}\"", "}"), NewsNotification::class.java
            )

        val pendingIntent = NavDeepLinkBuilder(baseContext)
            .setGraph(R.navigation.news_graph)
            .setDestination(R.id.action_newsFeedFragment_to_newsDetailsFragment)
            .setArguments(Bundle().also {
                if (notification.redirect_information.id != null)
                    it.putInt("news_id", notification.redirect_information.id)
                if (notification.redirect_information.type == "user_news")
                    it.putBoolean("user_news", true)
                it.putBoolean("from_push", true)
            })
            .createPendingIntent()

        val notificationBuilder = NotificationCompat.Builder(baseContext, CHANNEL_ID)
        notificationBuilder.setSmallIcon(R.drawable.ic_app_logo)
        notificationBuilder.setContentTitle(notification.header)
        notificationBuilder.setContentText(notification.text)
        notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        notificationBuilder.setContentIntent(pendingIntent)
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.color = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
        notificationBuilder.priority = NotificationCompat.PRIORITY_MAX

        try {
            var newsBitmap: Bitmap? = null
            Glide.with(this).asBitmap().load(notification.image)
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        newsBitmap = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

            newsBitmap?.let{
                    if (it.width > 512 && it.height > 512) {
                        notificationBuilder.setLargeIcon(it)
                        notificationBuilder.setStyle(
                            NotificationCompat
                                .BigPictureStyle()
                                .bigPicture(it)
                                .bigLargeIcon(it)
                        )
                    }
                }
        } catch (e: Exception) {
            Log.w("BreakingNewsFcmService", e.toString())
        }

        val newNotification = notificationBuilder.build()
        manager.notify(notification.id, newNotification)
    }
}