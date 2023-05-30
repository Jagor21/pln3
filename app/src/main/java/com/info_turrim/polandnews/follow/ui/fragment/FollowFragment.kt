package com.info_turrim.polandnews.follow.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.databinding.FragmentFollowBinding
import com.info_turrim.polandnews.follow.ui.controller.FollowController
import com.info_turrim.polandnews.follow.ui.view_model.FollowViewModel

class FollowFragment : BaseFragment<FragmentFollowBinding>(R.layout.fragment_follow) {

    override val viewModel by viewModels<FollowViewModel> { viewModelFactory }

    private lateinit var followController: FollowController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initController()
        observeViewModel()
        viewModel.getUserCategories()
    }

    private fun initController() {
        binding.rvFollow.layoutManager = GridLayoutManager(requireContext(), 3)
        followController = FollowController(requireContext())
        followController.listener = ::onEventClick
        binding.rvFollow.setControllerAndBuildModels(followController)
    }

    private fun observeViewModel() {
        viewModel.apply {
            categories.observe(viewLifecycleOwner) {
                followController.categoryList = it
            }
        }
    }

    private fun onEventClick(event: ModelViewEvent) {
        when (event) {
            is ModelViewEvent.SectionEvent -> {
                when (event) {
                    is ModelViewEvent.SectionEvent.SectionClickEvent -> {
//                        navController.navigate(
//                            R.id.action_global_to_sectionDetailsFragment,
//                            bundleOf(
//                                "category" to event.category,
//                                "sectionName" to event.sectionName
//                            )
//                        )
                    }
                    else -> {
                        viewModel.subscribeForCategory((event as ModelViewEvent.SectionEvent.SectionSubscribeEvent).id)
                    }
                }
            }

            is ModelViewEvent.FollowEvent.OnAddSectionClick -> {
                navController.navigate(
                    R.id.action_followFragment_to_sectionsGraph,
                    bundleOf("isSectionStart" to false)
                )
            }
            else -> {}
        }
    }
}