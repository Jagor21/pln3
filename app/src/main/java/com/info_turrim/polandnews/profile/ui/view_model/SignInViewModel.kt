package com.info_turrim.polandnews.profile.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.options.data.models.SignUpEmailRequest
import com.info_turrim.polandnews.options.domain.model.SignUpProfile
import com.info_turrim.polandnews.profile.domain.use_case.SignInUseCase
import com.info_turrim.polandnews.sections.domain.use_case.GoogleSignInUseCase
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
) : BaseViewModel() {


    private val _profileResult = MutableLiveData<SignUpProfile>()
    val profileResult: LiveData<SignUpProfile>
        get() = _profileResult

    private val _signInResult = MutableLiveData<SignUpProfile>()
    val signInResult: LiveData<SignUpProfile>
        get() = _signInResult

    fun signInEmail(signInEmailRequest: SignUpEmailRequest) {
        launchUseCase {
            signInUseCase.execute(signInEmailRequest) {
                it.fold(
                    onSuccess = {
                        _signInResult.value = it
                    },
                    onFailure = {

                    }
                )
            }
        }
    }

    fun googleSignIn(idToken: String) {
        launchUseCase {
            googleSignInUseCase.execute(idToken) {
                it.fold(
                    onSuccess = {
                        _profileResult.value = it
                    },
                    onFailure = {

                    }
                )
            }
        }
    }
}