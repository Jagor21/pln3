package com.info_turrim.polandnews.start_screen.ui.view_model

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.info_turrim.polandnews.auth.data.model.TokenResponse
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.options.data.models.SignUpEmailRequest
import com.info_turrim.polandnews.options.domain.model.SignUpProfile
import com.info_turrim.polandnews.profile.domain.use_case.SignInUseCase
import com.info_turrim.polandnews.sections.domain.use_case.GoogleSignInUseCase
import com.info_turrim.polandnews.start_screen.domain.use_case.*
import com.info_turrim.polandnews.utils.extension.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SplashScreenViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val getFollowedSectionsUseCase: GetFollowedSectionsUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val checkTokenUseCase: CheckTokenUseCase,
    private val reportFirstLaunchStatusUseCase: ReportFirstLaunchStatusUseCase,
    private val fakeSignUpUseCase: FakeSignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val googleSignInUseCase: GoogleSignInUseCase
) : BaseViewModel() {

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int>
        get() = _progress

    private val _needOpenSections = MutableLiveData<Boolean>()
    val needOpenSections: LiveData<Boolean>
        get() = _needOpenSections

    private val _needOpenTerms = MutableLiveData<Boolean>()
    val needOpenTerms: LiveData<Boolean>
        get() = _needOpenTerms

    private val _needOpenFeed = MutableLiveData<Boolean>()
    val needOpenFeed: LiveData<Boolean>
        get() = _needOpenFeed

    private val _needOpenStart = MutableSharedFlow<Boolean>(1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    val needOpenStart = _needOpenStart.asSharedFlow()

    private val _profileData = MutableLiveData<SignUpProfile>()
    val profileData: LiveData<SignUpProfile>
        get() = _profileData

    private val _atLeastOneSectionSelected = MutableLiveData<Boolean>()
    val atLeastOneSectionSelected: LiveData<Boolean>
        get() = _atLeastOneSectionSelected

    private var progressValue = 0

    fun load() {
        updateProgress()
        if (prefs.getIsUserLoggedIn()) {
            updateProgress()
            launchUseCase {
                updateProgress()
                refreshTokenUseCase.execute(
                    com.info_turrim.polandnews.auth.data.model.TokenRequest(
                        prefs.getRefreshToken()
                    )
                ) {
                    it.fold(
                        onSuccess = {
                            updateProgress()
                            prefs.setToken(it)
                            _needOpenFeed.value = true
                        },
                        onFailure = { error ->
                            if (error is Result.ErrorResult.NetworkErrorResponse) {
                                if (error.code == 401) {
                                    signIn(
                                        SignUpEmailRequest(
                                            city = null,
                                            country = null,
                                            email = prefs.getUserEmail(),
                                            gclid = null,
                                            password = prefs.getUserPassword(),
                                            sex = null,
                                            username = null,
                                            year_of_birth = null
                                        )
                                    )
                                }
                            }
                        }
                    )
                }
            }
        } else {
            updateProgress()
            if (prefs.getFirstLaunch()) {
                updateProgress()
                prefs.setFirstLaunch(false)
                updateProgress()
                _needOpenTerms.value = true
            } else {
                updateProgress()
                if(prefs.isTermsPolicyAccepted()) {
                    _needOpenStart.tryEmit(true)
                } else {
                    _needOpenTerms.value = true
                }
                updateProgress()
            }
        }
    }

    private fun signIn(param: SignUpEmailRequest) {
        launchUseCase {
            signInUseCase.execute(param) {
                it.fold(
                    onSuccess = {
                        prefs.setProfile(it.profile)
                        prefs.setToken(
                            TokenResponse(
                                access_token = it.accessToken,
                                refresh_token = it.refreshToken
                            )
                        )
                        prefs.setIsUserLoggedIn(true)
                        prefs.setIsUserReal(true)
                        _needOpenFeed.value = true
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun reportFirstLaunchStatus(status: Boolean) {
//        launchUseCase {
//            reportFirstLaunchStatusUseCase.execute(status) {
//                it.fold(
//                    onSuccess = {},
//                    onFailure = {}
//                )
//            }
//        }
    }

    private fun updateProgress(value: Int = 25) {
        _progress.value = progressValue
        progressValue += value
    }

    fun loadProgress() {
        viewModelScope.launch {
            repeat(6) {
                delay(1000)
                _progress.value = progressValue
                progressValue += 20
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