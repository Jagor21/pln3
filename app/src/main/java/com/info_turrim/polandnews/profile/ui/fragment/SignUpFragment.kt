package com.info_turrim.polandnews.profile.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentSignUpBinding
import com.info_turrim.polandnews.options.data.models.SignUpEmailRequest
import com.info_turrim.polandnews.profile.ui.view_model.SignUpViewModel
import com.info_turrim.polandnews.utils.extension.EMPTY
import com.info_turrim.polandnews.utils.extension.*

private const val EMAIL_ADDRESS_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"

private const val PASSWORD_PATTERN = "[A-Za-z0-9.,_ \\\\s-]{6,12}"

private const val NAME_PATTERN = "([A-Za-z0-9-]+)"

private const val DEFAULT_ERROR = 0

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    override val viewModel by viewModels<SignUpViewModel> { viewModelFactory }

    private var sexChoice = String.EMPTY
    private var yearChoice = String.EMPTY

    companion object {
        fun getInstance(): SignUpFragment {
            val fragment = SignUpFragment()
//            fragment.arguments = bundleOf(POSITION to position, USER_DATA to userData)
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initYears()
        manageChips()
        bindView()
        setInputFieldsChangedListener()
        observeViewModel()
    }

    private fun initYears() {
        val yearList = mutableListOf(getString(R.string.year_of_birth))
        yearList.addAll(resources.getStringArray(R.array.year_list).toList())
        val yearsAdapter = ArrayAdapter(requireContext(), R.layout.list_item_year, yearList)
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.yearList.apply {
            adapter = yearsAdapter
            onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        yearChoice = yearList[position]
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
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

            tilPassword.editText?.let {
                it.addTextChangedListener {
                    if (checkPassword() == DEFAULT_ERROR) {
                        tilPassword.hideError()
                    }
                }
            }

            tilPasswordAgain.editText?.let {
                it.addTextChangedListener {
                    if (checkPasswordAgain() == DEFAULT_ERROR) {
                        tilPasswordAgain.hideError()
                    }
                }
            }
        }
    }

    private fun manageChips() {
        binding.apply {
            maleChip.setOnCheckedChangeListener { btn, isChecked ->
                btn as Chip
                sexChoice = btn.text.toString()
                tvSexError.isGone = true
                btn.chipBackgroundColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.tab_indicator_color)
                btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                femaleChip.chipBackgroundColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)
                femaleChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                otherChip.chipBackgroundColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)
                otherChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

            }

            femaleChip.setOnCheckedChangeListener { btn, isChecked ->
                btn as Chip
                sexChoice = btn.text.toString()
                tvSexError.isGone = true
                btn.chipBackgroundColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.tab_indicator_color)
                btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                maleChip.chipBackgroundColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)
                maleChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                otherChip.chipBackgroundColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)
                otherChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            }

            otherChip.setOnCheckedChangeListener { btn, isChecked ->
                btn as Chip
                sexChoice = btn.text.toString()
                tvSexError.isGone = true
                btn.chipBackgroundColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.tab_indicator_color)
                btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                maleChip.chipBackgroundColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)
                maleChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                femaleChip.chipBackgroundColor =
                    ContextCompat.getColorStateList(requireContext(), R.color.white)
                femaleChip.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            }
        }
    }

    private fun bindView() {
        binding.apply {
            btnSignUp.setOnClickListener {
                if (checkSignUpFields()) {
                    viewModel.signUp(
                        SignUpEmailRequest(
                            city = null,
                            country = null,
                            email = etEmail.text.toString(),
                            gclid = null,
                            password = etPassword.text.toString(),
                            sex = getSexId(),
                            username = etName.text.toString(),
                            year_of_birth = yearChoice.toInt()
                        )
                    )
                }
            }
        }
    }

    private fun checkSignUpFields(): Boolean {
        binding.apply {
            val nameError = checkName(etName.text.toString())
            val emailError = checkEmail(etEmail.text.toString())
            val sexError = checkSex()
            val yearError = checkYearOfBirth()
            val passwordError = checkPassword()
            val passwordAgainError = checkPasswordAgain()

            return when {
                nameError != DEFAULT_ERROR -> {
                    tilName.showError(getString(nameError))
                    false
                }
                emailError != DEFAULT_ERROR -> {
                    tilEmail.showError(getString(emailError))
                    false
                }
                sexError != DEFAULT_ERROR -> {
                    tvSexError.isGone = false
                    tvSexError.text = getString(sexError)
                    false
                }
                yearError != DEFAULT_ERROR -> {
                    tvYearError.isGone = false
                    tvYearError.text = getString(yearError)
                    false
                }
                passwordError != DEFAULT_ERROR -> {
                    tilPassword.showError(getString(passwordError))
                    false
                }
                passwordAgainError != DEFAULT_ERROR -> {
                    tilPasswordAgain.showError(getString(passwordAgainError))
                    false
                }
                else -> true
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

    private fun checkSex(): Int {
        binding.tvSexError.isGone = true
        return when {
            sexChoice.isEmpty() -> R.string.error_sex_choose
            else -> DEFAULT_ERROR
        }
    }

    private fun checkYearOfBirth(): Int {
        binding.tvYearError.isGone = true
        return when {
            yearChoice == getString(R.string.year_of_birth) -> R.string.error_year_choose
            else -> DEFAULT_ERROR
        }
    }

    private fun checkPassword(): Int {
        binding.apply {
            tilPassword.hideError()
            return when {
                etPassword.text.toString().isEmpty() -> R.string.error_password_empty
                etPassword.text.toString().length <= 5 -> R.string.error_password_short
                !etPassword.text.toString()
                    .matches(Regex(PASSWORD_PATTERN)) -> R.string.error_password_characters_length
                else -> DEFAULT_ERROR
            }
        }
    }

    private fun checkPasswordAgain(): Int {
        binding.apply {
            tilPasswordAgain.hideError()
            return when {
                etPasswordAgain.text.toString().isEmpty() ||
                        etPasswordAgain.text.toString() != etPassword.text.toString() -> R.string.error_password_again_match
                else -> DEFAULT_ERROR
            }
        }
    }

    private fun getSexId(): Int {
        return when (sexChoice) {
            "Male" -> 1
            "Female" -> 2
            else -> 3
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            mError.observe(viewLifecycleOwner) {
                showError(message = it)
            }
            profileData.observe(viewLifecycleOwner) {
                prefs.setProfile(it.profile)
                prefs.setUserPassword(binding.etPassword.text.toString())
                prefs.setToken(
                    com.info_turrim.polandnews.auth.data.model.TokenResponse(
                        access_token = it.accessToken,
                        refresh_token = it.refreshToken
                    )
                )
                prefs.setIsUserLoggedIn(true)
                prefs.setIsUserReal(true)
                navController.navigate(R.id.action_authFragment_to_newsGraph)
            }
        }
    }
}