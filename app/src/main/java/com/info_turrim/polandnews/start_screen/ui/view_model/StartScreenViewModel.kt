package com.info_turrim.polandnews.start_screen.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.info_turrim.polandnews.MainActivity
import com.info_turrim.polandnews.auth.data.model.TokenResponse
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.start_screen.domain.use_case.FakeSignUpUseCase
import com.info_turrim.polandnews.options.domain.model.SignUpProfile
import com.info_turrim.polandnews.sections.domain.use_case.GoogleSignInUseCase
import com.info_turrim.polandnews.start_screen.domain.use_case.GetFollowedSectionsUseCase
import com.info_turrim.polandnews.start_screen.domain.use_case.RefreshTokenUseCase
import com.info_turrim.polandnews.utils.extension.EMPTY
import com.info_turrim.polandnews.sections.domain.use_case.GoogleSignUpUseCase
import com.info_turrim.polandnews.start_screen.domain.use_case.SendPushTokenUseCase
import com.info_turrim.polandnews.utils.extension.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.info_turrim.polandnews.start_screen.data.model.PushTokenParam
import javax.inject.Inject

class StartScreenViewModel @Inject constructor(
    private val fakeSignInUseCase: FakeSignUpUseCase,
    private val getFollowedSectionsUseCase: GetFollowedSectionsUseCase,
    private val googleSignUpUseCase: GoogleSignUpUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val sendPushTokenUseCase: SendPushTokenUseCase,
) : BaseViewModel() {

    private val _fakeSignUpResult = MutableLiveData<Boolean>()
    val fakeSignUpResult: LiveData<Boolean>
        get() = _fakeSignUpResult

    private val _atLeastOneSectionSelected = MutableLiveData<Boolean>()
    val atLeastOneSectionSelected: LiveData<Boolean>
        get() = _atLeastOneSectionSelected

    private val _profileResult = MutableLiveData<SignUpProfile>()
    val profileResult: LiveData<SignUpProfile>
        get() = _profileResult

    private var randomPass = String.EMPTY

    fun fakeSignUp() {
        launchUseCase {
            fakeSignInUseCase.execute(createSignUpFakeRequest()) {
                it.fold(
                    onSuccess = {
                        prefs.setToken(
                            TokenResponse(
                                access_token = it.accessToken,
                                refresh_token = it.refreshToken
                            )
                        )
                        prefs.setProfile(it.profile)
                        prefs.setUserPassword(randomPass)
                        prefs.setIsUserLoggedIn(true)
                        prefs.setIsUserReal(false)
                        sendNotificationToken()
                        _fakeSignUpResult.value = true
                    },
                    onFailure = {
                        _fakeSignUpResult.value = false
                    }
                )
            }
        }
    }

    fun getFollowedSections() {
        launchUseCase {
            getFollowedSectionsUseCase.execute(Unit) {
                it.fold(
                    onSuccess = {
                        _atLeastOneSectionSelected.value = it.isNotEmpty()
                    },
                    onFailure = {
                    }
                )
            }
        }
    }

    fun googleSignUp(idToken: String) {
        launchUseCase {
            googleSignUpUseCase.execute(idToken) {
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

    fun refreshToken(googleAccountToken: String) {
        launchUseCase {
            refreshTokenUseCase.execute(
                com.info_turrim.polandnews.auth.data.model.TokenRequest(
                    prefs.getRefreshToken()
                )
            ) {
                it.fold(
                    onSuccess = {
                        prefs.setToken(it)
                        googleSignIn(googleAccountToken)
                    },
                    onFailure = {

                    }
                )
            }
        }
    }

    private fun createSignUpFakeRequest(): com.info_turrim.polandnews.auth.data.model.SignUpFakeRequest {
        val randomName = getRandomString(10)
        randomPass = getRandomString(12)

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

    fun sendNotificationToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(
                    MainActivity::class.simpleName,
                    "Fetching FCM registration token failed",
                    task.exception
                )
                return@OnCompleteListener
            }
            val token = task.result
            launchUseCase {
                sendPushTokenUseCase.execute(PushTokenParam(token = token)) {
                    it.fold(
                        onSuccess = { tokenResult ->
                            Log.d("PUSH_TOKEN", tokenResult.result)
                        },
                        onFailure = {}
                    )
                }
            }
        })
    }
}