package com.info_turrim.polandnews.options.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.start_screen.domain.use_case.FakeSignUpUseCase
import com.info_turrim.polandnews.utils.extension.*
import javax.inject.Inject

class OptionsViewModel @Inject constructor(
    private val fakeSignInUseCase: FakeSignUpUseCase
) : BaseViewModel() {

    private val _fakeSignUpResult = MutableLiveData<Boolean>()
    val fakeSignUpResult: LiveData<Boolean>
        get() = _fakeSignUpResult

    fun fakeSignUp() {
        launchUseCase {
            fakeSignInUseCase.execute(createSignUpFakeRequest()) {
                it.fold(
                    onSuccess = {
                        prefs.setToken(
                            com.info_turrim.polandnews.auth.data.model.TokenResponse(
                                access_token = it.accessToken,
                                refresh_token = it.refreshToken
                            )
                        )
                        prefs.setProfile(it.profile)
                        prefs.setIsUserLoggedIn(true)
                        prefs.setIsUserReal(false)
                        _fakeSignUpResult.value = true
                    },
                    onFailure = {
                        _fakeSignUpResult.value = false
                    }
                )
            }
        }
    }

    private fun createSignUpFakeRequest(): com.info_turrim.polandnews.auth.data.model.SignUpFakeRequest {
        val randomName = getRandomString(10)
        val randomPass = getRandomString(12)

        return com.info_turrim.polandnews.auth.data.model.SignUpFakeRequest(
            "London",
            "$randomName@breaking-news.app", randomPass, randomName, false
        )

    }

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}