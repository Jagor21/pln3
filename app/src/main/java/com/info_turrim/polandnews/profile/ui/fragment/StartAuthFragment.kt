package com.info_turrim.polandnews.profile.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentStartAuthBinding
import com.info_turrim.polandnews.profile.ui.view_model.StartAuthViewModel
import com.info_turrim.polandnews.utils.extension.getIsUserReal

class StartAuthFragment : BaseFragment<FragmentStartAuthBinding>(R.layout.fragment_start_auth) {

    override val viewModel by viewModels<StartAuthViewModel> { viewModelFactory }

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

        if (prefs.getIsUserReal()) {
            navController.navigate(R.id.profile_fragment)
        }

        bindViews()
    }

    private fun bindViews() {
        binding.apply {
            onSignInClick = View.OnClickListener {
                navController.navigate(R.id.auth_fragment, bundleOf("isSignIn" to true))
            }
            onSignUpClick = View.OnClickListener {
                navController.navigate(R.id.auth_fragment, bundleOf("isSignIn" to false))
            }
        }
    }
}