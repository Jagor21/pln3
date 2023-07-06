package com.info_turrim.polandnews.sections.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.base.ModelViewEvent
import com.info_turrim.polandnews.databinding.FragmentSectionsBinding
import com.info_turrim.polandnews.sections.ui.view_model.SectionsViewModel
import com.info_turrim.polandnews.sections.ui.controller.SectionsController
import com.info_turrim.polandnews.utils.extension.EMPTY
private const val CATEGORY_ADDED_MSG = "Category added."
private const val CATEGORY_REMOVED_MSG = "Category removed."
class SectionsFragment : BaseFragment<FragmentSectionsBinding>(R.layout.fragment_sections) {

    private lateinit var sectionsController: SectionsController

    override val viewModel by viewModels<SectionsViewModel> { viewModelFactory }

    private val args by navArgs<SectionsFragmentArgs>()

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
        viewModel.getCategories()
        setController()
        bindViews()
        viewModel.apply {
            categories.observe(viewLifecycleOwner, Observer {
                sectionsController.categoryList = it
            })
            subscriptionResult.observe(viewLifecycleOwner, Observer { subscriptionResult ->
                sectionsController.categoryList = sectionsController.categoryList.map {
                    if (it.id == subscriptionResult.first) {
                        Toast.makeText(requireContext(), getString(
                            if(subscriptionResult.second == CATEGORY_ADDED_MSG) {
                                R.string.section_added
                            } else {
                                R.string.section_removed
                            }
                        ), Toast.LENGTH_SHORT).show()
                        it.copy(followedByUser = subscriptionResult.second != String.EMPTY)
                    } else {
                        it
                    }
                }
            })
        }
    }

    private fun setController() {
        sectionsController = SectionsController(requireContext())
        sectionsController.listener = ::onClickEvent
        sectionsController.isSectionStart = args.isSectionStart
        binding.rvSections.setController(sectionsController)
    }

    private fun onClickEvent(event: ModelViewEvent) {
        when (event) {
            is ModelViewEvent.SectionEvent -> {
                when (event) {
                    is ModelViewEvent.SectionEvent.SectionClickEvent -> {
                        navController.navigate(
                            R.id.action_sectionsFragment_to_sectionDetailsFragment,
                            bundleOf("category" to event.category, "sectionName" to event.sectionName)
                        )
                    }
                    else -> {
                        viewModel.subscribeForCategory((event as ModelViewEvent.SectionEvent.SectionSubscribeEvent).id)
                    }
                }
            }
            else -> {}
        }
    }

    private fun bindViews() {
        binding.apply {
            onProceedButtonClick = View.OnClickListener {
                navController.navigate(R.id.action_sectionsFragment_to_newsGraph)
            }

            isSectionStart = args.isSectionStart

            rvSections.layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }
}