package com.info_turrim.polandnews.profile.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.*
import com.info_turrim.polandnews.R
import com.info_turrim.polandnews.base.BaseFragment
import com.info_turrim.polandnews.databinding.FragmentSignInBinding
import com.info_turrim.polandnews.options.data.models.SignUpEmailRequest
import com.info_turrim.polandnews.profile.ui.view_model.SignInViewModel
import com.info_turrim.polandnews.utils.extension.*

private const val EMAIL_ADDRESS_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+"

private const val PASSWORD_PATTERN = "[A-Za-z0-9.,_ \\\\s-]{6,12}"

private const val DEFAULT_ERROR = 0

private const val RC_SIGN_IN = 1

class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    override val viewModel by viewModels<SignInViewModel> { viewModelFactory }

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object {
        fun getInstance(): SignInFragment {
            val fragment = SignInFragment()
//            fragment.arguments = bundleOf(POSITION to position, USER_DATA to userData)
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
        setInputFieldsChangedListener()
        observeViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

//                val account = task.getResult(ApiException::class.java)

            task.addOnSuccessListener { account ->
                account.idToken?.let { token ->
                    viewModel.googleSignIn(token)
                }
            }

            task.addOnFailureListener {
                showError(it.message.orEmpty(), it.localizedMessage)
//                Timber.d(account.toString())
            }
        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            signInResult.observe(viewLifecycleOwner) {

                prefs.setProfile(it.profile)
                prefs.setToken(
                    com.info_turrim.polandnews.auth.data.model.TokenResponse(
                        access_token = it.accessToken,
                        refresh_token = it.refreshToken
                    )
                )
                prefs.setUserPassword(binding.etPassword.text.toString())
                prefs.setIsUserLoggedIn(true)
                prefs.setIsUserReal(true)
                navController.navigate(R.id.action_authFragment_to_newsGraph)
            }

            profileResult.observe(viewLifecycleOwner) {
                prefs.setToken(
                    com.info_turrim.polandnews.auth.data.model.TokenResponse(
                        access_token = it.accessToken,
                        refresh_token = it.refreshToken
                    )
                )
                prefs.setProfile(it.profile)
                prefs.setIsUserLoggedIn(true)
                prefs.setIsUserReal(true)
                navController.navigate(R.id.action_authFragment_to_newsGraph)
            }
        }
    }

    private fun bindViews() {
        binding.apply {
            onSignInClick = View.OnClickListener {
                if (checkFields()) {
                    viewModel.signInEmail(
                        SignUpEmailRequest(
                            city = null,
                            country = null,
                            email = etEmail.text.toString(),
                            gclid = null,
                            password = etPassword.text.toString(),
                            sex = null,
                            username = null,
                            year_of_birth = null
                        )
                    )
                }
            }

            onGoogleSignInClick = View.OnClickListener {
//                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestIdToken(getString(R.string.google_sign_in_client_id))
//                    .requestEmail()
//                    .build()
//
//                mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
//
//                val intent = mGoogleSignInClient.signInIntent
//                startActivityForResult(intent, RC_SIGN_IN)
            }
        }
    }

    private fun setInputFieldsChangedListener() {
        binding.apply {
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
        }
    }

    private fun checkFields(): Boolean {
        binding.apply {
            val passwordError = checkPassword()
            val emailError = checkEmail(etEmail.text.toString())

            return when {
                passwordError != DEFAULT_ERROR -> {
                    tilPassword.showError(getString(passwordError))
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

    private fun checkEmail(email: String): Int {
        binding.tilEmail.hideError()
        return when {
            email.isEmpty() -> R.string.error_email_empty
            !email.matches(Regex(EMAIL_ADDRESS_PATTERN)) -> R.string.error_email_invalid
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
}