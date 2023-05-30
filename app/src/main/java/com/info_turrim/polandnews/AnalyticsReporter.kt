package com.info_turrim.polandnews

import android.content.Context

class AnalyticsReporter(val context: Context) {
//
//    @Inject
//    lateinit var sharedPreferences: SharedPreferences
//
//    @Inject
//    lateinit var authRepository: AuthRepository
//
//    private val ANALYTICS_NOTIFICATION_RECEIVED = "app_notification_receive"
//    private val ANALYTICS_NOTIFICATION_DISMISS = "app_notification_dismiss"
//    private val ANALYTICS_NOTIFICATION_OPEN = "app_notificcation_open"
//
//    private val ANALYTICS_ADS_INT_REQUEST = "ads_interstitial"
//    private val ANALYTICS_ADS_NATIVE_REQUEST = "ads_native"
//    private val ANALYTICS_ADS_BANNER_REQUEST = "ads_banner"
//
//    private val ANALYTICS_APP_SIGNUP = "app_signup"
//    private val ANALYTICS_APP_SIGNIN = "app_signin"
//
//    private val ANALYTICS_APP_FIRSTLAUNCH = "app_first_launch"
//
//    enum class Actions(val action: String) {
//        ANALYTICS_ACTIONS_LIKE("app_actions_like"),
//        ANALYTICS_ACTIONS_SHARE("app_actions_share"),
//        ANALYTICS_ACTIONS_POST_OPEN("app_actions_post_open")
//    }
//
//    private val mFirebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)
////    private val mYandexMetrica = YandexMetrica.getReporter(context, context.getString(R.string.Appmetrica_API_Key))
////    private val mAppsFlyerLib = AppsFlyerLib.getInstance()
//
//    fun getHashMapFromQuery(query: String): Map<String, String>? {
//        val query_pairs: MutableMap<String, String> =
//            LinkedHashMap()
//
//        if (query.isNullOrEmpty())
//            return query_pairs
//
//        val pairs = query.split("&".toRegex()).toTypedArray()
//        for (pair in pairs) {
//            val idx = pair.indexOf("=")
//
//            if (idx == -1)
//                return query_pairs
//
//            query_pairs[URLDecoder.decode(pair.substring(0, idx), "UTF-8")] =
//                URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
//        }
//        return query_pairs
//    }
//
//    fun reportInstallRefferer() {
//        val referrerClient = InstallReferrerClient.newBuilder(context).build()
//        referrerClient.startConnection(object : InstallReferrerStateListener {
//            override fun onInstallReferrerSetupFinished(responseCode: Int) {
//                when (responseCode) {
//                    InstallReferrerClient.InstallReferrerResponse.OK -> {
//                        try {
//
//                            val response: ReferrerDetails = referrerClient.installReferrer
//
//                            val getParams = getHashMapFromQuery(response.installReferrer)
//
//                            val newsid = getParams?.get("newsid")
//                            if (!newsid.isNullOrEmpty()) {
//                                EventBus.getDefault().post(ReferrerNewsIdEvent(newsid))
//                            }
//
//                            val click_id = getParams?.get("click_id")
//                            if (click_id.isNullOrEmpty()) {
//                                    authRepository.reportClickId(click_id!!)
//                            }
//
//                            val params = Bundle()
//                            params.putString(
//                                FirebaseAnalytics.Param.SOURCE,
//                                getParams.get("utm_source")
//                            )
//                            params.putString(
//                                FirebaseAnalytics.Param.MEDIUM,
//                                getParams.get("utm_medium")
//                            )
//                            params.putString(
//                                FirebaseAnalytics.Param.TERM,
//                                getParams.get("utm_term")
//                            )
//                            params.putString(
//                                FirebaseAnalytics.Param.CONTENT,
//                                getParams.get("utm_content")
//                            )
//                            mFirebaseAnalytics.logEvent(
//                                FirebaseAnalytics.Event.CAMPAIGN_DETAILS,
//                                params
//                            )
//
//                            sharedPreferences.setRefReported()
//                        } catch (e: Exception)
//                        {
//
//                        }
//                    }
//                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
//                        // API not available on the current Play Store app.
//                    }
//                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
//                        // Connection couldn't be established.
//                    }
//                }
//            }
//
//            override fun onInstallReferrerServiceDisconnected() {
//                // Try to restart the connection on the next request to
//                // Google Play by calling the startConnection() method.
//            }
//        })
//    }
//
//    fun reportNotificationReceived(showNotification: Boolean) {
//        //Firebase analytics
//        val firebaseParams = Bundle()
//        firebaseParams.putBoolean("showNotification", showNotification)
//        mFirebaseAnalytics.logEvent(ANALYTICS_NOTIFICATION_RECEIVED, firebaseParams)
//
//        //Yandex Appmetrica
//        val eventParameters: Map<String, Boolean> = HashMap()
//        (eventParameters as MutableMap<String, Boolean>)["showNotification"] = showNotification
////        mYandexMetrica.reportEvent(ANALYTICS_NOTIFICATION_RECEIVED, eventParameters)
//
//        //Appsflyer
////        mAppsFlyerLib.trackEvent(context, ANALYTICS_NOTIFICATION_RECEIVED, eventParameters)
//    }
//
//    fun reportNotificationDismiss() {
//        //Firebase analytics
//        mFirebaseAnalytics.logEvent(ANALYTICS_NOTIFICATION_DISMISS, null)
//        //Yandex Appmetrica
////        mYandexMetrica.reportEvent(ANALYTICS_NOTIFICATION_DISMISS)
//        //Appsflyer
////        mAppsFlyerLib.trackEvent(context, ANALYTICS_NOTIFICATION_DISMISS, null)
//    }
//
//    fun reportNotificationOpen(id: Int) {
//        //Firebase analytics
//        val firebaseParams = Bundle()
//        firebaseParams.putInt("news_id", id)
//        mFirebaseAnalytics.logEvent(ANALYTICS_NOTIFICATION_OPEN, firebaseParams)
//
//        //Yandex Appmetrica
//        val eventParameters: Map<String, Int> = HashMap()
//        (eventParameters as MutableMap<String, Int>)["news_id"] = id
////        mYandexMetrica.reportEvent(ANALYTICS_NOTIFICATION_OPEN, eventParameters)
//
//        //Appsflyer
////        mAppsFlyerLib.trackEvent(context, ANALYTICS_NOTIFICATION_OPEN, eventParameters)
//    }
//
//    fun reportIntRequestStatus(status: String) {
//        //Firebase analytics
//        val firebaseParams = Bundle()
//        firebaseParams.putString("status", status)
//        mFirebaseAnalytics.logEvent(ANALYTICS_ADS_INT_REQUEST, firebaseParams)
//
//        //Yandex Appmetrica
//        val eventParameters: Map<String, String> = HashMap()
//        (eventParameters as MutableMap<String, String>)["status"] = status
////        mYandexMetrica.reportEvent(ANALYTICS_ADS_INT_REQUEST, eventParameters)
//
//        //Appsflyer
////        mAppsFlyerLib.trackEvent(context, ANALYTICS_ADS_INT_REQUEST, eventParameters)
//    }
//
//    fun reportNativeRequestStatus(status: String) {
//        //Firebase analytics
//        val firebaseParams = Bundle()
//        firebaseParams.putString("status", status)
//        mFirebaseAnalytics.logEvent(ANALYTICS_ADS_NATIVE_REQUEST, firebaseParams)
//
//        //Yandex Appmetrica
//        val eventParameters: Map<String, String> = HashMap()
//        (eventParameters as MutableMap<String, String>)["status"] = status
////        mYandexMetrica.reportEvent(ANALYTICS_ADS_NATIVE_REQUEST, eventParameters)
//
//        //Appsflyer
////        mAppsFlyerLib.trackEvent(context, ANALYTICS_ADS_NATIVE_REQUEST, eventParameters)
//    }
//
//    fun reportFirstLaunchStatus(status: Boolean) {
//        //Firebase analytics
//        val firebaseParams = Bundle()
//        firebaseParams.putBoolean("status", status)
//        mFirebaseAnalytics.logEvent(ANALYTICS_APP_FIRSTLAUNCH, firebaseParams)
//
//        //Yandex Appmetrica
//        val eventParameters: Map<String, Boolean> = HashMap()
//        (eventParameters as MutableMap<String, Boolean>)["status"] = status
////        mYandexMetrica.reportEvent(ANALYTICS_APP_FIRSTLAUNCH, eventParameters)
//
//        //Appsflyer
////        mAppsFlyerLib.trackEvent(context, ANALYTICS_APP_FIRSTLAUNCH, eventParameters)
//    }
//
//    fun reportBannerRequestStatus(status: String) {
//        //Firebase analytics
//        val firebaseParams = Bundle()
//        firebaseParams.putString("status", status)
//        mFirebaseAnalytics.logEvent(ANALYTICS_ADS_BANNER_REQUEST, firebaseParams)
//
//        //Yandex Appmetrica
//        val eventParameters: Map<String, String> = HashMap()
//        (eventParameters as MutableMap<String, String>)["status"] = status
////        mYandexMetrica.reportEvent(ANALYTICS_ADS_BANNER_REQUEST, eventParameters)
//
//        //Appsflyer
////        mAppsFlyerLib.trackEvent(context, ANALYTICS_ADS_BANNER_REQUEST, eventParameters)
//    }
//
//    fun reportAction(action: String, postId: Int) {
//        //Firebase analytics
//        val firebaseParams = Bundle()
//        firebaseParams.putInt("post_id", postId)
//        mFirebaseAnalytics.logEvent(action, firebaseParams)
//
//        //Yandex Appmetrica
//        val eventParameters: Map<String, Int> = HashMap()
//        (eventParameters as MutableMap<String, Int>)["post_id"] = postId
////        mYandexMetrica.reportEvent(action, eventParameters)
//
//        //Appsflyer
////        mAppsFlyerLib.trackEvent(context, action, eventParameters)
//    }
//
//    fun reportSignUp() {
//        //Firebase analytics
//        mFirebaseAnalytics.logEvent(ANALYTICS_APP_SIGNUP, null)
//        //Yandex Appmetrica
////        mYandexMetrica.reportEvent(ANALYTICS_APP_SIGNUP)
//        //Appsflyer
////        mAppsFlyerLib.trackEvent(context, ANALYTICS_APP_SIGNUP, null)
//    }
//
//    fun reportSignIn() {
//        //Firebase analytics
//        mFirebaseAnalytics.logEvent(ANALYTICS_APP_SIGNIN, null)
//        //Yandex Appmetrica
////        mYandexMetrica.reportEvent(ANALYTICS_APP_SIGNIN)
//        //Appsflyer
////        mAppsFlyerLib.trackEvent(context, ANALYTICS_APP_SIGNIN, null)
//    }
}