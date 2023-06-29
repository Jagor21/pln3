package com.info_turrim.polandnews

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.info_turrim.polandnews.common.analytics_event.ReferrerNewsIdEvent
import com.info_turrim.polandnews.databinding.ActivityMainBinding
import com.info_turrim.polandnews.network.ConnectivityStateHolder
import com.info_turrim.polandnews.network.Event
import com.info_turrim.polandnews.network.NetworkEvents
import com.info_turrim.polandnews.news_feed.data.model.GetAdRequestParam
import com.info_turrim.polandnews.news_feed.domain.model.News
import com.info_turrim.polandnews.utils.extension.getUUID
import com.info_turrim.polandnews.utils.extension.setUUID
import dagger.android.support.DaggerAppCompatActivity
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var prefs: SharedPreferences

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<MainActivityViewModel> { viewModelFactory }

    private var previousSate = true

    var adList: List<News> = listOf()

    private var currentDestination: Int = -1
    var uuid: String = ""

    private val permissionsList = listOf(
        android.Manifest.permission.POST_NOTIFICATIONS
    )

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        }

    private val destinationChangedListener: NavController.OnDestinationChangedListener by lazy {
        NavController.OnDestinationChangedListener { _, destination, args ->
            when (destination.id) {

                R.id.sections_fragment -> {
                    args?.let {
                        binding.bottomNavView.isGone = it.getBoolean("isSectionStart")
                    }
                }

                R.id.news_feed_fragment -> {
                    binding.bottomNavView.selectedItemId = R.id.news_graph
                    binding.bottomNavView.isGone = false
                }

                R.id.follow_fragment,
                R.id.profile_fragment,
                R.id.source_fragment,
                R.id.start_auth_fragment,
                -> {
                    binding.bottomNavView.isGone = false
                }

                else -> {
                    binding.bottomNavView.isGone = true
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val host = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = host.navController

        navController.addOnDestinationChangedListener(destinationChangedListener)
        binding.bottomNavView.setOnItemSelectedListener {
            if (it.itemId != currentDestination) {
                when (it.itemId) {
//                    currentDestination -> false
                    R.id.news_graph -> {
                        currentDestination = R.id.news_graph
                        navController.navigate(R.id.news_graph)
                        true
                    }

                    R.id.sections_graph -> {
                        currentDestination = R.id.sections_graph
                        navController.navigate(R.id.sections_graph)
                        true
                    }

                    R.id.follow_graph -> {
                        currentDestination = R.id.follow_graph
                        navController.navigate(R.id.follow_graph)
                        true
                    }

                    R.id.profile_graph -> {
                        currentDestination = R.id.profile_graph
                        navController.navigate(R.id.profile_graph)
                        true
                    }

                    else -> false
                }
            } else {
                false
            }

        }
//        binding.bottomNavView.setupWithNavController(navController)

        savedInstanceState?.let {
            previousSate = it.getBoolean("LOST_CONNECTION")
        }


        NetworkEvents.observe(this, Observer {
            if (it is Event.ConnectivityEvent)
                handleConnectivityChange()
        })

        binding.bottomNavView.isGone = true
        setLocale("pl")
        uuid = prefs.getUUID()
        if (uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString()
            prefs.setUUID(uuid)
        }

        requestPermission(permissionsList)

    }

//    fun getAd() {
//        viewModel.getAd(
//            GetAdRequestParam(
//                uuid = uuid,
//                adsQuantity = 10
//            )
//        )
//    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun requestPermission(permissionsList: List<String>) {
        val newPermissions = mutableListOf<String>()
        permissionsList.forEach { permission ->
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_DENIED
            ) {
                newPermissions.add(permission)
            }
        }
        if (newPermissions.isNotEmpty()) {
            requestPermissionLauncher.launch(newPermissions.toTypedArray())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("LOST_CONNECTION", previousSate)
        super.onSaveInstanceState(outState)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReffererNewsid(referrerNewsIdEvent: ReferrerNewsIdEvent) {
        openPost(referrerNewsIdEvent.newsid)
    }

    private fun handleConnectivityChange() {
        if (ConnectivityStateHolder.isConnected && !previousSate) {
            Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.network_connection_back_online),
                Snackbar.LENGTH_SHORT
            ).show()
        }

        if (!ConnectivityStateHolder.isConnected && previousSate) {
            Snackbar.make(
                findViewById(android.R.id.content),
                getString(R.string.network_connection_offline),
                Snackbar.LENGTH_SHORT
            ).show()
        }

        previousSate = ConnectivityStateHolder.isConnected
        binding.containerOffline.isGone = ConnectivityStateHolder.isConnected
    }

    private fun openPost(link: String) {
        try {
            if (link.contains("users-news")) {
                val id = link
                    .replace("${BuildConfig.API_URL}v1/users-news/", "")
                    .replace("/", "")
                    .toInt()

//                navController.navigate(R.id.postFragment, bundleOf("news_id" to id))
            } else {
                val id = link
                    .replace("${BuildConfig.API_URL}v1/news/", "")
                    .replace("/", "")
                    .toInt()

//                navController.navigate(R.id.postFragment, bundleOf("news_id" to id))
            }
        } catch (e: Exception) {
            navController.navigate(R.id.news_graph)
        }
    }
}



