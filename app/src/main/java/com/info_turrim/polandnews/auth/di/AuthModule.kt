package com.info_turrim.polandnews.auth.di

import com.info_turrim.polandnews.auth.data.repository.AuthRepositoryImpl
import com.info_turrim.polandnews.auth.data.repository.datasource.AuthDataSource
import com.info_turrim.polandnews.auth.data.repository.datasource.AuthDataSourceImpl
import com.info_turrim.polandnews.auth.domain.AuthRepository
import dagger.Binds
import dagger.Module

@Module
abstract class AuthModule {

    @Binds
    abstract fun bindAuthRepository(param: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindAuthDataSource(param: AuthDataSourceImpl): AuthDataSource
}