package com.info_turrim.polandnews.di

import com.info_turrim.polandnews.auth.data.remote.AuthApi
import com.info_turrim.polandnews.common.services.NewsService
import com.info_turrim.polandnews.follow.data.remote.FollowApi
import com.info_turrim.polandnews.news_feed.data.remote.AdApi
import com.info_turrim.polandnews.news_feed.data.remote.ForYouNewsApi
import com.info_turrim.polandnews.news_feed.data.remote.NewsApi
import com.info_turrim.polandnews.options.data.remote.OptionsApi
import com.info_turrim.polandnews.profile.data.remote.ProfileApi
import com.info_turrim.polandnews.sections.data.remote.CategoryApi
import com.info_turrim.polandnews.start_screen.data.remote.StartApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class
    ]
)
class ServiceModule {

    @Singleton
    @Provides
    fun provideNewsService(
        @Named("breaking-news-api") retrofit: Retrofit
    ): NewsService = retrofit.create()

    @Singleton
    @Provides
    fun provideCategoriesService(
        @Named("breaking-news-api") retrofit: Retrofit
    ): CategoryApi = retrofit.create()

    @Singleton
    @Provides
    fun provideStartService(
        @Named("breaking-news-api") retrofit: Retrofit
    ): StartApi = retrofit.create()

    @Singleton
    @Provides
    fun provideAuthService(
        @Named("breaking-news-api") retrofit: Retrofit
    ): AuthApi = retrofit.create()

    @Singleton
    @Provides
    fun provideNewsApi(
        @Named("breaking-news-api") retrofit: Retrofit
    ): NewsApi = retrofit.create()

    @Singleton
    @Provides
    fun provideFollowApi(
        @Named("breaking-news-api") retrofit: Retrofit
    ): FollowApi = retrofit.create()

    @Singleton
    @Provides
    fun provideProfileApi(
        @Named("breaking-news-api") retrofit: Retrofit
    ): ProfileApi = retrofit.create()

    @Singleton
    @Provides
    fun provideOptionsApi(
        @Named("breaking-news-api") retrofit: Retrofit
    ): OptionsApi = retrofit.create()

    @Singleton
    @Provides
    fun provideForYouNewsApi(
        @Named("breaking-news-for-you-api") retrofit: Retrofit
    ): ForYouNewsApi = retrofit.create()

    @Singleton
    @Provides
    fun provideAdApi(
        @Named("ad-api") retrofit: Retrofit
    ): AdApi = retrofit.create()
}