package com.info_turrim.polandnews.profile.ui.adapter

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.info_turrim.polandnews.profile.ui.fragment.AuthFragment
import com.info_turrim.polandnews.profile.ui.fragment.SignInFragment
import com.info_turrim.polandnews.profile.ui.fragment.SignUpFragment
import dagger.android.support.DaggerFragment

private const val AUTH_SIZE = 2

class AuthAdapter(
    fragment: AuthFragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return AUTH_SIZE
    }

    override fun createFragment(position: Int): DaggerFragment {
        return when (position) {
            0 -> SignInFragment.getInstance()
            else -> SignUpFragment.getInstance()
        }
    }
}