package com.info_turrim.polandnews.di

import androidx.lifecycle.ViewModel
import com.info_turrim.polandnews.start_screen.ui.fragment.SplashScreenFragment
import com.info_turrim.polandnews.start_screen.ui.fragment.StartScreenFragment
import com.info_turrim.polandnews.start_screen.ui.fragment.TermsFragment
import com.info_turrim.polandnews.start_screen.ui.view_model.SplashScreenViewModel
import com.info_turrim.polandnews.start_screen.ui.view_model.StartScreenViewModel
import com.info_turrim.polandnews.start_screen.ui.view_model.TermsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class SplashModule {

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun splashScreenFragment(): SplashScreenFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun startScreenFragment(): StartScreenFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun termsFragment(): TermsFragment

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel::class)
    internal abstract fun bindSplashScreenViewModel(splashScreenViewModel: SplashScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StartScreenViewModel::class)
    internal abstract fun bindStartScreenViewModel(startScreenViewModel: StartScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TermsViewModel::class)
    internal abstract fun bindTermsViewModel(termsViewModel: TermsViewModel): ViewModel
}