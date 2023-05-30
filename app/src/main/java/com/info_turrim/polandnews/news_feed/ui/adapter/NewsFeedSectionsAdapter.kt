package com.info_turrim.polandnews.news_feed.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commitNow
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.info_turrim.polandnews.news_feed.ui.fragment.NewsFeedSectionFragment

private const val NEWS_FEED_SECTIONS_SIZE = 5

class NewsFeedSectionsAdapter(
    fragmentManager: FragmentManager,
    private val newsFeedSectionList: List<Pair<Int, String>>,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    init {
        // Add a FragmentTransactionCallback to handle changing
        // the primary navigation fragment
        registerFragmentTransactionCallback(object : FragmentTransactionCallback() {
            override fun onFragmentMaxLifecyclePreUpdated(
                fragment: Fragment,
                maxLifecycleState: Lifecycle.State
            ) = if (maxLifecycleState == Lifecycle.State.RESUMED) {

                // This fragment is becoming the active Fragment - set it to
                // the primary navigation fragment in the OnPostEventListener
                OnPostEventListener {
                    fragment.parentFragmentManager.commitNow {
                        setPrimaryNavigationFragment(fragment)
                    }
                }

            } else {
                super.onFragmentMaxLifecyclePreUpdated(fragment, maxLifecycleState)
            }
        })
    }

    override fun getItemCount(): Int {
        return newsFeedSectionList.size
    }

    override fun createFragment(position: Int): NewsFeedSectionFragment {
        return NewsFeedSectionFragment.getInstance(position,
            newsFeedSectionList[position].second.substringBefore(" ")
                .replaceFirstChar { it.lowercase() },
            categoryId = newsFeedSectionList[position].first
        )
    }
}