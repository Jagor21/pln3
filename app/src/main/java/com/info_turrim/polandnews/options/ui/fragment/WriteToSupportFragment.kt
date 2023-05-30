package com.info_turrim.polandnews.options.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentWriteToSupportBinding
import com.info_turrim.polandnews.options.data.models.SupportRequest
import com.info_turrim.polandnews.options.ui.view_model.WriteToSupportViewModel
import com.info_turrim.polandnews.utils.extension.EMPTY
import com.info_turrim.polandnews.utils.extension.getUserEmail

class WriteToSupportFragment :
    BaseFragment<FragmentWriteToSupportBinding>(R.layout.fragment_write_to_support) {

    override val viewModel by viewModels<WriteToSupportViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        observeViewModel()
    }

    private fun bindViews() {
        binding.apply {
            onSendClick = View.OnClickListener {
                val message = etMessage.text.toString()
                etMessage.error = null
                if (message.isNotEmpty()) {
                    viewModel.sendToSupport(
                        SupportRequest(
                        email = prefs.getUserEmail(),
                        header = String.EMPTY,
                        text = message
                    )
                    )
                } else {
                    etMessage.error = getString(R.string.empty_message)
                }
            }

            tbWriteToSupport.setNavigationOnClickListener { navController.navigateUp() }
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            supportResult.observe(viewLifecycleOwner) {
                showSnackbarMessage(getString(R.string.your_message_was_sent))
                navController.navigateUp()
            }
        }
    }
}