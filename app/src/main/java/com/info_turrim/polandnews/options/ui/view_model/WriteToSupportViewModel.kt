package com.info_turrim.polandnews.options.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.info_turrim.polandnews.base.BaseViewModel
import com.info_turrim.polandnews.options.data.models.SupportRequest
import com.info_turrim.polandnews.options.domain.model.Support
import com.info_turrim.polandnews.options.domain.use_case.SendToSupportUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class WriteToSupportViewModel @Inject constructor(
    private val sendToSupportUseCase: SendToSupportUseCase
) : BaseViewModel() {

    private val _supportResult = MutableLiveData<Support>()
    val supportResult: LiveData<Support>
        get() = _supportResult

    fun sendToSupport(supportRequest: SupportRequest) {
        launchUseCase {
            delay(Random.nextLong(1000L, 3000L))
            _supportResult.value =
                Support(created_at = "", email = "", header = "", id = -1, text = "")
//            sendToSupportUseCase.execute(supportRequest) {
//                it.fold(
//                    onSuccess = {
//                        _supportResult.value = it
//                    },
//                    onFailure = {
//
//                    }
//                )
//            }
        }
    }
}