package com.info_turrim.polandnews.options.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.*
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.databinding.FragmentOptionsBinding
import com.info_turrim.polandnews.options.ui.OptionsType
import com.info_turrim.polandnews.options.ui.controller.OptionsController
import com.info_turrim.polandnews.options.ui.view_model.OptionsViewModel
import com.info_turrim.polandnews.utils.extension.*

class OptionsFragment : BaseFragment<FragmentOptionsBinding>(R.layout.fragment_options) {

    override val viewModel by viewModels<OptionsViewModel> { viewModelFactory }

    private lateinit var optionsController: OptionsController

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initController()
        bindViews()
        observeViewModel()
    }

    private fun initController() {
        optionsController = OptionsController(requireContext())
        optionsController.listener = ::onEventClick
        binding.rvOptions.setControllerAndBuildModels(optionsController)
    }

    private fun bindViews() {
        binding.apply {
            tbOptions.setNavigationOnClickListener {
                navController.navigateUp()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            fakeSignUpResult.observe(viewLifecycleOwner) {
                if (it) {
                    navController.navigate(R.id.action_optionsFragment_to_newsGraph)
                }
            }
        }
    }

    private fun onEventClick(event: ModelViewEvent) {
        when (event) {
            is ModelViewEvent.OptionsEvent -> {
                when (event) {
                    is ModelViewEvent.OptionsEvent.OnOptionClick -> {
                        when (event.optionType) {
                            OptionsType.EDIT_PROFILE -> {
                                navController.navigate(
                                    R.id.action_optionsFragment_to_editProfileFragment,
                                    bundleOf(
                                        "userName" to prefs.getUserName(),
                                        "userEmail" to prefs.getUserEmail()
                                    )
                                )
                            }
                            OptionsType.WRITE_TO_SUPPORT -> {
                                navController.navigate(R.id.action_optionsFragment_to_writeSupportFragment)
                            }
                            OptionsType.APP_RATING -> {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse(
                                        "https://play.google.com/store/apps/details?id=com.info_turrim.polandnews")
                                    setPackage("com.android.vending")
                                }
                                startActivity(intent)
                            }
                            OptionsType.SHARE_APP -> {
                                val shareIntent = Intent(Intent.ACTION_SEND).also {
                                    it.type = "text/plain"
                                    it.putExtra(Intent.EXTRA_SUBJECT, "Poland News")
                                }
                                val shareMessage = "\nSprawdź to!\n\n" +
                                        "https://play.google.com/store/apps/details?id=com.info_turrim.polandnews"
                                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                                startActivity(Intent.createChooser(shareIntent, "Choose one"))
                            }
                            OptionsType.PRIVACY_POLICY -> {
                                navController.navigate(
                                    R.id.action_optionsFragment_to_optionsDocFragment,
                                    bundleOf("isPolicy" to true)
                                )
                            }
                            OptionsType.ABOUT -> {
                                navController.navigate(R.id.action_optionsFragment_to_aboutFragment)
                            }
                            OptionsType.TERMS_OF_USE -> {
                                navController.navigate(
                                    R.id.action_optionsFragment_to_optionsDocFragment,
                                    bundleOf("isPolicy" to false)
                                )
                            }
                        }
                    }
                    is ModelViewEvent.OptionsEvent.OnLogOutClick -> {
                        prefs.clearUserData()
//                        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
//                        if (account != null) {
//                            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                                .requestIdToken(getString(R.string.google_sign_in_client_id))
//                                .requestEmail()
//                                .build()
//
//                            mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
//
//                            mGoogleSignInClient.signOut().addOnCompleteListener {
//                                viewModel.fakeSignUp()
//                            }
//                        } else {
                        viewModel.fakeSignUp()
//                        }

                    }
                    else -> {
                    }
                }
            }
            else -> {
            }
        }
    }
}