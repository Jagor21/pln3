package com.info_turrim.polandnews.start_screen.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.*
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentSplashScreenBinding
import com.info_turrim.polandnews.start_screen.ui.view_model.SplashScreenViewModel
import com.info_turrim.polandnews.utils.extension.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding>(R.layout.fragment_splash_screen) {

    override val viewModel by viewModels<SplashScreenViewModel> { viewModelFactory }

    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account != null) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.load()
        observeViewModel()

//        binding.tvPleaseWait.setOnClickListener {
//            navController.navigate(R.id.action_splashScreenFragment_to_newsFeedFragment)
//        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                binding.pbProgress.progress = it
            })

            needOpenSections.observe(viewLifecycleOwner, Observer {
                if (it) {
                    navController.navigate(
                        R.id.action_splashScreenFragment_to_sectionsGraph,
                        bundleOf("isSectionStart" to true)
                    )
                }
            })

            needOpenTerms.observe(viewLifecycleOwner, Observer {
                if (it) {
                    navController.navigate(R.id.action_splashScreenFragment_to_termsFragment, null)
                }
            })
            needOpenFeed.observe(viewLifecycleOwner, Observer {
                if (it) {
                    navController.navigate(R.id.action_splashScreenFragment_to_newsGraph)
                }
            })
            needOpenStart.onEach {
                if (it) {
                    navController.navigate(R.id.action_splashScreenFragment_to_startScreenFragment)
                }
            }.launchIn(lifecycleScope)

            profileData.observe(viewLifecycleOwner) {
                prefs.setProfile(it.profile)
                prefs.setToken(
                    com.info_turrim.polandnews.auth.data.model.TokenResponse(
                        access_token = it.accessToken,
                        refresh_token = it.refreshToken
                    )
                )
                prefs.setIsUserLoggedIn(true)
                prefs.setIsUserReal(
                    prefs.getUserEmail().substringAfter("@") != "breaking-news.app"
                )
                getFollowedSections()
            }
            atLeastOneSectionSelected.observe(viewLifecycleOwner) {
                if (it) {
                    navController.navigate(R.id.action_splashScreenFragment_to_newsGraph)
                } else {
                    navController.navigate(R.id.action_splashScreenFragment_to_sectionsGraph)
                }
            }
        }
    }
}