package com.info_turrim.polandnews.profile.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.options.data.models.SignUpEmailRequest
import com.info_turrim.polandnews.options.domain.model.SignUpProfile
import com.info_turrim.polandnews.profile.domain.use_case.SignUpUseCase
import java.lang.StringBuilder
import java.util.*
import javax.inject.Inject
import com.info_turrim.polandnews.base.Result

class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : BaseViewModel() {

    private val _profileData = MutableLiveData<SignUpProfile>()
    val profileData: LiveData<SignUpProfile>
        get() = _profileData

    private val _error = MutableLiveData<String>()
    val mError: LiveData<String>
        get() = _error

    fun signUp(signUpEmailRequest: SignUpEmailRequest) {
        launchUseCase {
            signUpUseCase.execute(signUpEmailRequest) {
                it.fold(
                    onSuccess = { profile ->
                        _profileData.value = profile
                    },
                    onFailure = {
                        it as Result.ErrorResult.NetworkErrorResponse

                        if (!it.errorsList.isNullOrEmpty()) {
                            val stringBuilder = StringBuilder()

                            it.errorsList.forEach { errorPair ->
                                stringBuilder.append(errorPair.first.replaceFirstChar {
                                    if (it.isLowerCase()) {
                                        it.titlecase(Locale.getDefault())
                                    } else {
                                        it.toString()
                                    }
                                })
                                stringBuilder.append(": ")
                                stringBuilder.append(
                                    errorPair.second
                                        .replace("[\"", "")
                                        .replace("\"]", "")
                                )
                                stringBuilder.append("\n\n")
                            }
                            _error.value = stringBuilder.toString()
                        } else {
                            _error.value = it.errorMessage
                        }
                    }
                )
            }
        }
    }
}