package com.info_turrim.polandnews.base

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var prefs: SharedPreferences

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun launchUseCase(
        needToShowProgressBar: Boolean = false,
        block: suspend () -> Unit
    ): Job = viewModelScope.launch {
        try {
            _loading.value = needToShowProgressBar
            block()
        } catch (e: Exception) {
            _error.value = e.localizedMessage
            Log.e("MY_TAG", "", e)
            Timber.e(e)
        } finally {
            _loading.value = false
        }
    }

    fun loading(isLoading: Boolean) {
        _loading.value = isLoading
    }
}