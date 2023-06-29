package com.info_turrim.polandnews.news_feed.ui.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.common.model_domain.Category
import com.info_turrim.polandnews.databinding.FragmentNewsFeedBinding
import com.info_turrim.polandnews.news_feed.ui.adapter.NewsFeedSectionsAdapter
import com.info_turrim.polandnews.news_feed.ui.view_model.NewsFeedViewModel


private const val CATEGORIES_BUNDLE_KEY = "categories"

class NewsFeedFragment : BaseFragment<FragmentNewsFeedBinding>(R.layout.fragment_news_feed) {

    override val viewModel by viewModels<NewsFeedViewModel>() { viewModelFactory }

    private lateinit var newsFeedSectionsAdapter: NewsFeedSectionsAdapter

    private var categories = emptyArray<Category>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (categories.isEmpty()) {
            viewModel.getCategories()
            observeViewModel()
        } else {
            setupViewPager(categories.toList())
        }
    }

    private fun setupViewPager(categoryList: List<Category>) {
        val categories = mutableListOf(-1 to "for-you")
        categories.addAll(categoryList.map { it.id to it.title.replaceFirstChar { it.titlecase() } })

        newsFeedSectionsAdapter = NewsFeedSectionsAdapter(childFragmentManager, categories, lifecycle)
        with(binding) {
            vpNewsFeedContainer.adapter = newsFeedSectionsAdapter
            vpNewsFeedContainer.isUserInputEnabled = false

//            tlTabs.addOnTabSelectedListener(object : OnTabSelectedListener {
//                override fun onTabSelected(tab: TabLayout.Tab?) {
//                    tab?.let {
//                        navController.navigate(
//                            R.id.action_newsFeedFragment_to_newsFeedSectionFragment,
//                            bundleOf("position" to tab.position,
//                            "filter_type" to categories[tab.position].second.substringBefore(" "),
//                            "category_id" to categories[tab.position].first)
//                        )
//
//                    }
//                }
//
//                override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//                }
//
//                override fun onTabReselected(tab: TabLayout.Tab?) {
//
//                }
//            })

            TabLayoutMediator(tlTabs, vpNewsFeedContainer) { tab, position ->
//                tab.text = categories[position].second
                tab.text = when (position) {
                    0 -> getString(R.string.tab_title_for_you)
                    else -> categories[position].second
                }
            }.attach()
//adding margins to the tabs
            for (i in 0 until tlTabs.tabCount) {
                val tab = (tlTabs.getChildAt(0) as ViewGroup).getChildAt(i)
                val p = tab.layoutParams as MarginLayoutParams
                p.setMargins(0, 0, 10, 0)
                tab.requestLayout()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArray(CATEGORIES_BUNDLE_KEY, categories)
    }

    fun observeViewModel() {
        viewModel.apply {
            categories.observe(viewLifecycleOwner) {
                this@NewsFeedFragment.categories = it.toTypedArray()
                setupViewPager(it)
            }
        }
    }
}