package com.info_turrim.polandnews.di

import com.info_turrim.polandnews.MainActivity
import com.info_turrim.polandnews.auth.di.AuthModule
import com.info_turrim.polandnews.di.scopes.ActivityScoped
import com.info_turrim.polandnews.follow.di.FollowModule
import com.info_turrim.polandnews.news_feed.di.NewsFeedModule
import com.info_turrim.polandnews.options.di.OptionsModule
import com.info_turrim.polandnews.profile.di.ProfileModule
import com.info_turrim.polandnews.sections.di.SectionsModule
import com.info_turrim.polandnews.source.di.SourceModule
import com.info_turrim.polandnews.start_screen.di.StartModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            ViewModelModule::class,
            SplashModule::class,
            SectionsModule::class,
            StartModule::class,
            AuthModule::class,
            NewsFeedModule::class,
            SourceModule::class,
            FollowModule::class,
            ProfileModule::class,
            OptionsModule::class,
        ]
    )
    internal abstract fun mainActivity(): MainActivity
}