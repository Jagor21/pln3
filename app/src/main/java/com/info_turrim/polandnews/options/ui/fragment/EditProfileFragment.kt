package com.info_turrim.polandnews.options.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentEditProfileBinding
import com.info_turrim.polandnews.options.data.models.EditProfileRequest
import com.info_turrim.polandnews.options.ui.view_model.EditProfileViewModel
import com.info_turrim.polandnews.utils.extension.*

private const val EMAIL_ADDRESS_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"


private const val NAME_PATTERN = "([A-Za-z0-9-]+)"

private const val DEFAULT_ERROR = 0

class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile) {

    override val viewModel by viewModels<EditProfileViewModel> { viewModelFactory }

    private val args by navArgs<EditProfileFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        setInputFieldsChangedListener()
        observeViewModel()
    }

    private fun bindViews() {
        binding.apply {
            userName = args.userName
            userEmail = args.userEmail
            onSaveChangesClick = View.OnClickListener {
                if (checkFields()) {
                    viewModel.saveChanges(
                        EditProfileRequest(
                            username = etName.text.toString(),
                            email = etEmail.text.toString()
                        )
                    )
                }
            }

            tbEditProfile.setNavigationOnClickListener {
                navController.navigateUp()
            }
        }
    }

    private fun checkFields(): Boolean {
        binding.apply {
            val nameError = checkName(etName.text.toString())
            val emailError = checkEmail(etEmail.text.toString())

            return when {
                nameError != DEFAULT_ERROR -> {
                    tilName.showError(getString(nameError))
                    false
                }
                emailError != DEFAULT_ERROR -> {
                    tilEmail.showError(getString(emailError))
                    false
                }
                else -> true
            }
        }
    }

    private fun setInputFieldsChangedListener() {
        binding.apply {
            tilName.editText?.let {
                it.addTextChangedListener {
                    if (checkName(it.toString()) == DEFAULT_ERROR) {
                        tilName.hideError()
                    }
                }
            }

            tilEmail.editText?.let {
                it.addTextChangedListener {
                    if (checkEmail(it.toString()) == DEFAULT_ERROR) {
                        tilEmail.hideError()
                    }
                }
            }
        }
    }

    private fun checkName(name: String): Int {
        binding.tilName.hideError()
        return when {
            name.isEmpty() -> R.string.error_name_empty
            name.length <= 1 -> R.string.error_name_short
            !name.matches(Regex(NAME_PATTERN)) -> R.string.error_name_wrong_letters
            else -> DEFAULT_ERROR
        }
    }

    private fun checkEmail(email: String): Int {
        binding.tilEmail.hideError()
        return when {
            email.isEmpty() -> R.string.error_email_empty
            !email.matches(Regex(EMAIL_ADDRESS_PATTERN)) -> R.string.error_email_invalid
            else -> DEFAULT_ERROR
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            editProfileResult.observe(viewLifecycleOwner) {
                prefs.setUserEmail(it.email)
                prefs.setUserName(it.username)
                showSnackbarMessage(getString(R.string.profile_was_updated))
                navController.navigateUp()
            }
        }
    }
}