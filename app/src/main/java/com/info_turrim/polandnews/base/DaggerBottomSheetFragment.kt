package com.info_turrim.polandnews.base

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.*
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class DaggerBottomSheetFragment : BottomSheetDialogFragment(), HasAndroidInjector {

    @Inject
    internal lateinit var childFragmentInjector: DispatchingAndroidInjector<Any>

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun androidInjector(): AndroidInjector<Any> =
        childFragmentInjector

}