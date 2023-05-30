package com.info_turrim.polandnews.start_screen.di

import com.info_turrim.polandnews.start_screen.data.repository.StartRepositoryImpl
import com.info_turrim.polandnews.start_screen.data.repository.datasource.StartDataSource
import com.info_turrim.polandnews.start_screen.data.repository.datasource.StartDataSourceImpl
import com.info_turrim.polandnews.start_screen.domain.StartRepository
import dagger.Binds
import dagger.Module

@Module
abstract class StartModule {

    @Binds
    abstract fun bindStartRepository(param: StartRepositoryImpl): StartRepository

    @Binds
    abstract fun bindStartDataSource(param: StartDataSourceImpl): StartDataSource
}