package com.info_turrim.polandnews.profile.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.databinding.FragmentProfileSectionsBinding
import com.info_turrim.polandnews.profile.ui.controller.ProfileSectionsController
import com.info_turrim.polandnews.profile.ui.view_model.ProfileSectionsViewModel

class ProfileSectionsFragment :
    BaseFragment<FragmentProfileSectionsBinding>(R.layout.fragment_profile_sections) {

    override val viewModel by viewModels<ProfileSectionsViewModel> { viewModelFactory }

    private lateinit var profileSectionsController: ProfileSectionsController

    companion object {
        fun getInstance(): ProfileSectionsFragment {
            val fragment = ProfileSectionsFragment()
//            fragment.arguments = bundleOf(POSITION to position, USER_DATA to userData)
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserCategories()
        initController()
        bindViewModel()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun initController() {
        profileSectionsController = ProfileSectionsController(requireContext())
        profileSectionsController.listener = ::onClickEvent
        binding.rvSections.setController(profileSectionsController)
    }

    private fun onClickEvent(event: ModelViewEvent) {
        when (event) {
            is ModelViewEvent.SectionEvent -> {
                when (event) {
                    is ModelViewEvent.SectionEvent.SectionClickEvent -> {
                        navController.navigate(
                            R.id.action_profileFragment_to_sectionDetailsFragment,
                            bundleOf(
                                "category" to event.category,
                                "sectionName" to event.sectionName
                            )
                        )
                    }
                    else -> {
                        viewModel.subscribeForCategory((event as ModelViewEvent.SectionEvent.SectionSubscribeEvent).id)
                    }
                }
            }

            is ModelViewEvent.ProfileEvent -> {
                when (event) {
                    is ModelViewEvent.ProfileEvent.OnAddSectionClick -> {
                        navController.navigate(
                            R.id.action_profileFragment_to_sectionsGraph,
                            bundleOf("isSectionStart" to false)
                        )
                    }
                    is ModelViewEvent.ProfileEvent.OnUnsubscribeCategoryClick -> {
                        navController.navigate(
                            R.id.action_profileFragment_to_hideSectionBottomSheetDialogFragment,
                            bundleOf("categoryId" to event.categoryId)
                        )
                    }
                    else -> {
                    }
                }
            }
            else -> {
            }
        }
    }

    private fun bindViewModel() {
        viewModel.apply {
            categories.observe(viewLifecycleOwner) {
                binding.apply {
                    if (!it.isNullOrEmpty()) {
                        tvNoSections.isGone = true
                        btnAddFirstSection.isGone = true
                        rvSections.isGone = false
                        tvSectionsTitle.isGone = false
                        profileSectionsController.categoryList = it
                    } else {
                        tvNoSections.isGone = false
                        btnAddFirstSection.isGone = false
                        rvSections.isGone = true
                        tvSectionsTitle.isGone = true
                    }
                }
            }
        }
    }
}