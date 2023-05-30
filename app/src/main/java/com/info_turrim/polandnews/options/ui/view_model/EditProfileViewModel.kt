package com.info_turrim.polandnews.options.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.options.data.models.EditProfileRequest
import com.info_turrim.polandnews.options.domain.model.EditProfile
import com.info_turrim.polandnews.options.domain.use_case.EditProfileUseCase
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val editProfileUseCase: EditProfileUseCase
) : BaseViewModel() {

    private val _editProfileResult = MutableLiveData<EditProfile>()
    val editProfileResult: LiveData<EditProfile>
        get() = _editProfileResult

    fun saveChanges(editProfileRequest: EditProfileRequest) {
        launchUseCase {
            editProfileUseCase.execute(editProfileRequest) {
                it.fold(
                    onSuccess = {
                        _editProfileResult.value = it
                    },
                    onFailure = {

                    }
                )
            }
        }
    }
}