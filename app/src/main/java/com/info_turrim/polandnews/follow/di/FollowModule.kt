package com.info_turrim.polandnews.follow.di

import androidx.lifecycle.ViewModel
import com.info_turrim.polandnews.di.ViewModelKey
import com.info_turrim.polandnews.follow.data.repository.FollowRepositoryImpl
import com.info_turrim.polandnews.follow.data.repository.datasource.FollowDataSource
import com.info_turrim.polandnews.follow.data.repository.datasource.FollowDataSourceImpl
import com.info_turrim.polandnews.follow.domain.repository.FollowRepository
import com.info_turrim.polandnews.follow.ui.fragment.FollowFragment
import com.info_turrim.polandnews.follow.ui.view_model.FollowViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FollowModule {

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFollowFragment(): FollowFragment

    @Binds
    abstract fun bindFollowRepository(param: FollowRepositoryImpl): FollowRepository

    @Binds
    abstract fun bindFollowDataSource(param: FollowDataSourceImpl): FollowDataSource

    @Binds
    @IntoMap
    @ViewModelKey(FollowViewModel::class)
    internal abstract fun bindFollowViewModel(followViewModel: FollowViewModel): ViewModel
}