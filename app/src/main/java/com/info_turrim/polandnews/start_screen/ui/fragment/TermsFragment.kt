package com.info_turrim.polandnews.start_screen.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentTermsBinding
import com.info_turrim.polandnews.start_screen.ui.view_model.TermsViewModel
import com.info_turrim.polandnews.utils.extension.setIsTermsPolicyAccepted

private const val PRIVACY_POLICY = "Polityką Prywatności"
private const val TERMS_OF_SERVICE = "Warunkami Korzystania"

class TermsFragment : BaseFragment<FragmentTermsBinding>(R.layout.fragment_terms) {

    override val viewModel by viewModels<TermsViewModel> { viewModelFactory }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        createSpannedTermsDescription()

    }

    private fun bindViews() {
        binding.apply {
            onProceedClick = View.OnClickListener {
                prefs.setIsTermsPolicyAccepted(true)
                navController.navigate(R.id.action_termsFragment_to_startScreenFragment)
            }
        }
    }

    private fun createSpannedTermsDescription() {

        val string = getString(R.string.terms_description)

        val spannableString = SpannableString(string)

        val indexPolicy = string.indexOf(PRIVACY_POLICY)
        val indexTerms = string.indexOf(TERMS_OF_SERVICE)

        val spanPolicy = object : ClickableSpan() {
            override fun onClick(p0: View) {
                navController.navigate(
                    R.id.action_termsFragment_to_startOptionsDocFragment,
                    bundleOf("isPolicy" to true)
                )
            }

        }

        val spanTerms = object : ClickableSpan() {
            override fun onClick(p0: View) {
                navController.navigate(
                    R.id.action_termsFragment_to_startOptionsDocFragment,
                    bundleOf("isPolicy" to false)
                )
            }
        }

        spannableString.setSpan(
            spanPolicy,
            indexPolicy,
            indexPolicy + PRIVACY_POLICY.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            spanTerms,
            indexTerms,
            indexTerms + TERMS_OF_SERVICE.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.apply {
            tvTermsDescription.setText(spannableString, TextView.BufferType.SPANNABLE)
            tvTermsDescription.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun TextView.setClickablePhrase(
        fullText: String?,
        varArg1: String? = null,
        varArg2: String? = null,
        clickablePhrase: String?,
        shouldBoldPhrase: Boolean? = false,
        shouldUnderlinePhrase: Boolean? = true,
        @ColorInt phraseColor: Int = ContextCompat.getColor(requireContext(), R.color.black),
        action: () -> Unit
    ) {
        if (fullText == null) {
            return
        }
        if (clickablePhrase == null) {
            return
        }

        val formattedFullText =
            if (varArg1 == null && varArg2 == null) {
                String.format(fullText, clickablePhrase)
            } else if (varArg1 != null && varArg2 == null) {
                String.format(fullText, varArg1, clickablePhrase)
            } else {
                String.format(fullText, varArg1, varArg2, clickablePhrase)
            }

        val spannableString = SpannableString(formattedFullText)
        val phraseIndex = formattedFullText.indexOf(clickablePhrase, 0)
        if (phraseIndex != -1) {
            // Make the clickable phrase bold if shouldBoldPhrase is set to true
            if (shouldBoldPhrase!!) {
                val boldSpan = StyleSpan(Typeface.BOLD)
                spannableString.setSpan(
                    boldSpan, phraseIndex,
                    phraseIndex + clickablePhrase.length,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }

            // Creates a clickable span, draws an underline and handles the clicks
            movementMethod = LinkMovementMethod.getInstance()
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    action()
                }

                override fun updateDrawState(drawState: TextPaint) {
                    super.updateDrawState(drawState)
                    drawState.isUnderlineText = shouldUnderlinePhrase!!
                    drawState.color = phraseColor
                }
            }

            spannableString.setSpan(
                clickableSpan, phraseIndex,
                phraseIndex + clickablePhrase.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        text = spannableString
    }

}