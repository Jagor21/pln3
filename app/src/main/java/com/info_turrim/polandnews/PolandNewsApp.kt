package com.info_turrim.polandnews

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.appsflyer.AppsFlyerLib
import com.google.firebase.FirebaseApp
import com.info_turrim.polandnews.di.DaggerAppComponent
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class PolandNewsApp : DaggerApplication() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig

    companion object {
        private var res: Resources? = null
    }

    override fun onCreate() {
        super.onCreate()
        res = resources
        initAppsFlyer()
        createNotificationsChannels()
        createSilentNotificationChannel()

        FirebaseApp.initializeApp(this)
        mFirebaseRemoteConfig  = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if(task.isSuccessful) {
                Log.d("FirebaseRemoteConfig", "config updated! task.result ${task.result}")
            } else {
                Log.d("FirebaseRemoteConfig", "Failed to update config")
            }
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    fun getAppResources(): Resources? {
        return res
    }

    private fun initAppsFlyer() {
        AppsFlyerLib.getInstance().setDebugLog(true)
        AppsFlyerLib.getInstance()
            .init(getString(R.string.appsflyer_dev_key), null, this)
        AppsFlyerLib.getInstance().start(this)
    }

    private fun createNotificationsChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.reminders_notification_channel_id),
                getString(R.string.reminders_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            ContextCompat.getSystemService(this, NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }
    }

    private fun createSilentNotificationChannel() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.getNotificationChannel(NewsPolandFCMService.SILENT_CHANNEL_ID) == null) {
                val channel = NotificationChannel(
                    NewsPolandFCMService.SILENT_CHANNEL_ID,
                    "NewsPoland silent events",
                    NotificationManager.IMPORTANCE_LOW
                )
                channel.enableLights(false)
                channel.enableVibration(false)
                channel.setSound(null, null)
                manager.createNotificationChannel(channel)
            }
        }
    }
}