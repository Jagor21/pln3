package com.info_turrim.polandnews.source.di

import androidx.lifecycle.ViewModel
import com.info_turrim.polandnews.di.ViewModelKey
import com.info_turrim.polandnews.source.data.repository.SourceRepositoryImpl
import com.info_turrim.polandnews.source.data.repository.datasource.SourceDataSource
import com.info_turrim.polandnews.source.data.repository.datasource.SourceDataSourceImpl
import com.info_turrim.polandnews.source.domain.SourceRepository
import com.info_turrim.polandnews.source.ui.fragment.SourceFragment
import com.info_turrim.polandnews.source.ui.view_model.SourceViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SourceModule {

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSourceFragment(): SourceFragment

    @Binds
    abstract fun bindSourceRepository(param: SourceRepositoryImpl): SourceRepository

    @Binds
    abstract fun bindSourceDataSource(param: SourceDataSourceImpl): SourceDataSource

    @Binds
    @IntoMap
    @ViewModelKey(SourceViewModel::class)
    internal abstract fun bindSourceViewModel(sourceViewModel: SourceViewModel): ViewModel
}