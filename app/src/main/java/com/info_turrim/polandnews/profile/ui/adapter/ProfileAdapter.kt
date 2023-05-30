package com.info_turrim.polandnews.profile.ui.adapter

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.info_turrim.polandnews.profile.ui.fragment.FavouritesFragment
import com.info_turrim.polandnews.profile.ui.fragment.ProfileFragment
import com.info_turrim.polandnews.profile.ui.fragment.ProfileSectionsFragment
import dagger.android.support.DaggerFragment

private const val PROFILE_SIZE = 2

class ProfileAdapter(
    profileFragment: ProfileFragment
) : FragmentStateAdapter(profileFragment) {

    override fun getItemCount(): Int {
        return PROFILE_SIZE
    }

    override fun createFragment(position: Int): DaggerFragment {
        return when (position) {
            0 -> FavouritesFragment.getInstance()
            else -> ProfileSectionsFragment.getInstance()
        }
    }
}