package com.info_turrim.polandnews.options.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.info_turrim.polandnews.BuildConfig
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentAboutBinding
import com.info_turrim.polandnews.options.ui.view_model.AboutViewModel

class AboutFragment : BaseFragment<FragmentAboutBinding>(R.layout.fragment_about) {

    override val viewModel by viewModels<AboutViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
    }

    private fun bindViews() {
        binding.apply {
            tbDoc.setNavigationOnClickListener { navController.navigateUp() }
            version = getString(R.string.version, BuildConfig.VERSION_NAME)

            onCheckUpdatesClick = View.OnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(
                        "https://play.google.com/store/apps/details?id=com.info_turrim.polandnews")
                    setPackage("com.android.vending")
                }
                startActivity(intent)
            }
            //TODO ask about the source of title and description
//            aboutTitle = "Title"
//            aboutDescription = getString(R.string.long_text_placeholder)
        }
    }
}