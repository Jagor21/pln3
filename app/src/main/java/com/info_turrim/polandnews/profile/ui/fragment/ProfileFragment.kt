package com.info_turrim.polandnews.profile.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentProfileBinding
import com.info_turrim.polandnews.profile.ui.adapter.ProfileAdapter
import com.info_turrim.polandnews.profile.ui.view_model.ProfileViewModel
import com.info_turrim.polandnews.utils.extension.getUserEmail
import com.info_turrim.polandnews.utils.extension.getUserName

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    override val viewModel by viewModels<ProfileViewModel> { viewModelFactory }

    private lateinit var profileAdapter: ProfileAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        bindViews()
    }

    private fun setupViewPager() {
        profileAdapter = ProfileAdapter(this)
        with(binding) {
            vpProfileContent.adapter = profileAdapter
            vpProfileContent.isUserInputEnabled = false
            TabLayoutMediator(tlProfile, vpProfileContent) { tab, position ->
                tab.text = when (position) {
                    0 -> getString(R.string.favorites_tab)
                    else -> getString(R.string.sections)
                }
            }.attach()
        }
    }

    private fun bindViews() {
        binding.apply {
            userName = prefs.getUserName()
            userEmail = prefs.getUserEmail()
            tbProfile.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.profile_settings -> {
                        navController.navigate(R.id.action_profileFragment_to_optionsFragment)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }
}