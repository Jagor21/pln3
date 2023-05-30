package com.info_turrim.polandnews.profile.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseBottomSheetFragment
import com.info_turrim.polandnews.databinding.FragmentHideSectionBottomSheetBinding
import com.info_turrim.polandnews.profile.ui.view_model.HideSectionBottomSheetDialogViewModel
import com.info_turrim.polandnews.utils.extension.EMPTY

class HideSectionBottomSheetDialogFragment :
    BaseBottomSheetFragment<FragmentHideSectionBottomSheetBinding>(R.layout.fragment_hide_section_bottom_sheet) {

    override val viewModel by viewModels<HideSectionBottomSheetDialogViewModel> { viewModelFactory }

    private val args by navArgs<HideSectionBottomSheetDialogFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            onPositiveClick = View.OnClickListener {
                args.categoryId?.let {
                    viewModel.onUnsubscribeSection(it)
                    dismiss()
                }
            }

            onNegativeClick = View.OnClickListener {
                dismiss()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.apply {
            unsubscribeResult.observe(viewLifecycleOwner) {
                if (it.second != String.EMPTY) {
                    dismiss()
                }
            }
        }
    }
}