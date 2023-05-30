package com.info_turrim.polandnews.options.di

import androidx.lifecycle.ViewModel
import com.info_turrim.polandnews.di.ViewModelKey
import com.info_turrim.polandnews.options.data.repository.OptionsRepositoryImpl
import com.info_turrim.polandnews.options.data.repository.datasource.OptionsDataSource
import com.info_turrim.polandnews.options.data.repository.datasource.OptionsDataSourceImpl
import com.info_turrim.polandnews.options.domain.OptionsRepository
import com.info_turrim.polandnews.options.ui.fragment.*
import com.info_turrim.polandnews.options.ui.view_model.*
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class OptionsModule {

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeOptionsFragment(): OptionsFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeAboutFragment(): AboutFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeEditProfileFragment(): EditProfileFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeOptionsDocFragment(): OptionsDocFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeWriteToSupportFragment(): WriteToSupportFragment

    @Binds
    abstract fun bindOptionsRepository(param: OptionsRepositoryImpl): OptionsRepository

    @Binds
    abstract fun bindOptionsDataSource(param: OptionsDataSourceImpl): OptionsDataSource

    @Binds
    @IntoMap
    @ViewModelKey(OptionsViewModel::class)
    internal abstract fun bindOptionsViewModel(optionsViewModel: OptionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    internal abstract fun bindAboutViewModel(aboutViewModel: AboutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    internal abstract fun bindEditProfileViewModel(editProfileViewModel: EditProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OptionsDocViewModel::class)
    internal abstract fun bindEditOptionsDocViewModel(optionsDocViewModel: OptionsDocViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WriteToSupportViewModel::class)
    internal abstract fun bindWriteToSupportViewModel(writeToSupportViewModel: WriteToSupportViewModel): ViewModel

}