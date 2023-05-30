package com.info_turrim.polandnews.start_screen.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.info_turrim.polandnews.MainActivity
import com.google.android.gms.auth.api.signin.*
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentStartScreenBinding
import com.info_turrim.polandnews.start_screen.ui.view_model.StartScreenViewModel
import com.info_turrim.polandnews.utils.extension.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

private const val RC_SIGN_IN = 1

class StartScreenFragment :
    BaseFragment<FragmentStartScreenBinding>(R.layout.fragment_start_screen) {

    override val viewModel by viewModels<StartScreenViewModel> { viewModelFactory }

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sendNotificationToken()
        bindViews()
        observeViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

//                val account = task.getResult(ApiException::class.java)

            task.addOnSuccessListener { account ->
                account.idToken?.let { token ->
                    viewModel.googleSignUp(token)
                }
            }

            task.addOnFailureListener {
                showError(it.message.orEmpty(), it.localizedMessage)
//                Timber.d(account.toString())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account != null) {
            account.idToken?.let { token ->
//                viewModel.googleSignIn(token)

//                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestIdToken(getString(R.string.google_sign_in_client_id))
//                    .requestEmail()
//                    .build()
//
//                mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
//
//                val intent = mGoogleSignInClient.signInIntent
//                startActivityForResult(intent, RC_SIGN_IN)
//
//                viewModel.refreshToken(token)
            }
        }
    }

    private fun bindViews() {
        binding.apply {
            onSkipClick = View.OnClickListener {
                viewModel.fakeSignUp()
            }
            onGoogleSignUpClick = View.OnClickListener {
//                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestIdToken(getString(R.string.google_sign_in_client_id))
//                    .requestEmail()
//                    .build()
//
//                mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
//
//                val intent = mGoogleSignInClient.signInIntent
//                startActivityForResult(intent, RC_SIGN_IN)
            }

            onEmailSignUpClick = View.OnClickListener {
                navController.navigate(R.id.action_startFragment_to_profileGraph)
            }

            onLogInClick = View.OnClickListener {
                navController.navigate(R.id.action_startFragment_to_profileGraph)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            fakeSignUpResult.observe(viewLifecycleOwner) {
                if (it) {
                    getFollowedSections()
                }
            }
            atLeastOneSectionSelected.observe(viewLifecycleOwner) {
                if (it) {
                    navController.navigate(R.id.action_startFragment_to_newsGraph)
                } else {
                    navController.navigate(
                        R.id.action_startScreenFragment_to_sectionsFragment,
                        bundleOf("isSectionStart" to true)
                    )
                }
            }
            profileResult.observe(viewLifecycleOwner) {
                prefs.setToken(
                    com.info_turrim.polandnews.auth.data.model.TokenResponse(
                        access_token = it.accessToken,
                        refresh_token = it.refreshToken
                    )
                )
                prefs.setProfile(it.profile)
                prefs.setIsUserLoggedIn(true)
                prefs.setIsUserReal(true)
                navController.navigate(R.id.action_startFragment_to_newsGraph)
            }
        }
    }
}