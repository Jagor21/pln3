package com.info_turrim.polandnews.di

import android.content.Context
import com.info_turrim.polandnews.AnalyticsReporter
import com.info_turrim.polandnews.PolandNewsApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesApplicationContext(application: PolandNewsApp) = application.applicationContext

    @Provides
    @Singleton
    fun provideAnalyticsReporter(context: Context) = AnalyticsReporter(context)
}