package com.info_turrim.polandnews.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factoryBreakingNews: BreakingNewsViewModelFactory):
            ViewModelProvider.Factory
}