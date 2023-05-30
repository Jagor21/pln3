package com.info_turrim.polandnews.profile.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentAuthBinding
import com.info_turrim.polandnews.profile.ui.adapter.AuthAdapter
import com.info_turrim.polandnews.profile.ui.view_model.AuthViewModel

class AuthFragment : BaseFragment<FragmentAuthBinding>(R.layout.fragment_auth) {

    override val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    private lateinit var authAdapter: AuthAdapter

    private val args by navArgs<AuthFragmentArgs>()

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
        setupViewPager()
        chooseTab()
    }

    private fun setupViewPager() {
        authAdapter = AuthAdapter(this)
        with(binding) {
            vpAuthContent.adapter = authAdapter
            vpAuthContent.isUserInputEnabled = false
            TabLayoutMediator(tlAuth, vpAuthContent) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.sign_in)
                    else -> getString(R.string.sign_up)
                }
            }.attach()
        }
    }

    private fun chooseTab() {
//        binding.tlAuth.setScrollPosition(if (args.isSignIn) 0 else 1, 0f, true)
        binding.vpAuthContent.currentItem = if (args.isSignIn) 0 else 1
        if (args.isSignIn) {
            binding.tlAuth.selectTab(binding.tlAuth.getTabAt(0))
            binding.vpAuthContent.currentItem = 0
        } else {
            binding.tlAuth.selectTab(binding.tlAuth.getTabAt(1))
        binding.vpAuthContent.currentItem = 1
        }
    }
}