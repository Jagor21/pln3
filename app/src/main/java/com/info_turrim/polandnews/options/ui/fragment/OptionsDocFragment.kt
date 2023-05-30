package com.info_turrim.polandnews.options.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentOptionsDocBinding
import com.info_turrim.polandnews.options.ui.view_model.OptionsDocViewModel
private const val TERMS = "file:///android_asset/terms.html"
private const val POLICY= "file:///android_asset/policy.html"
class OptionsDocFragment : BaseFragment<FragmentOptionsDocBinding>(R.layout.fragment_options_doc) {

    override val viewModel by viewModels<OptionsDocViewModel> { viewModelFactory }

    private val args by navArgs<OptionsDocFragmentArgs>()

    private var isPrivacyPolicy = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isPrivacyPolicy = args.isPolicy
        bindViews()
    }

    private fun bindViews() {
        binding.apply {
            tbDoc.setNavigationOnClickListener { navController.navigateUp() }
            toolbarTitle =
                getString(if (isPrivacyPolicy) R.string.privacy_policy else R.string.terms_of_use)

            //TODO ask about source of doc title and doc text
//            docTitle = getString(if (isPrivacyPolicy) R.string.privacy_policy else R.string.terms_of_use)
//            docText = getString(R.string.long_text_placeholder)
            wvDoc.loadUrl(
                if(isPrivacyPolicy) {
                    POLICY
                } else {
                    TERMS
                }
            )
        }
    }
}