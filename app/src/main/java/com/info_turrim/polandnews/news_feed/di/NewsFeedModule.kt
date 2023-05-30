package com.info_turrim.polandnews.news_feed.di

import androidx.lifecycle.ViewModel
import com.info_turrim.polandnews.MainActivityViewModel
import com.info_turrim.polandnews.di.ViewModelKey
import com.info_turrim.polandnews.news_feed.data.repository.CommentsRepositoryImpl
import com.info_turrim.polandnews.news_feed.data.repository.NewsDetailsRepositoryImpl
import com.info_turrim.polandnews.news_feed.data.repository.NewsRepositoryImpl
import com.info_turrim.polandnews.news_feed.data.repository.datasource.*
import com.info_turrim.polandnews.news_feed.domain.repository.CommentsRepository
import com.info_turrim.polandnews.news_feed.domain.repository.NewsDetailsRepository
import com.info_turrim.polandnews.news_feed.domain.repository.NewsRepository
import com.info_turrim.polandnews.news_feed.ui.fragment.*
import com.info_turrim.polandnews.news_feed.ui.view_model.*
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class NewsFeedModule {

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeNewsFeedFragment(): NewsFeedFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeNewsFeedSectionFragment(): NewsFeedSectionFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeNewsCommentsFragment(): NewsCommentsFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeNewsDetailsFragment(): NewsDetailsFragment


    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeLeaveCommentFragment(): LeaveCommentFragment

    @Binds
    abstract fun bindNewsRepository(param: NewsRepositoryImpl): NewsRepository

    @Binds
    abstract fun bindNewsDataSource(param: NewsDataSourceImpl): NewsDataSource

    @Binds
    abstract fun bindCommentsRepository(param: CommentsRepositoryImpl): CommentsRepository

    @Binds
    abstract fun bindCommentsDataSource(param: CommentsDataSourceImpl): CommentsDataSource

    @Binds
    abstract fun bindNewsDetailsRepository(param: NewsDetailsRepositoryImpl): NewsDetailsRepository

    @Binds
    abstract fun bindNewsDetailsDataSource(param: NewsDetailsDataSourceImpl): NewsDetailsDataSource

    @Binds
    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    internal abstract fun bindNewsFeedViewModel(newsFeedViewModel: NewsFeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsFeedSectionViewModel::class)
    internal abstract fun bindNewsFeedSectionViewModel(newsFeedSectionViewModel: NewsFeedSectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsCommentsViewModel::class)
    internal abstract fun bindNewsCommentsViewModel(newsCommentsViewModel: NewsCommentsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsDetailsViewModel::class)
    internal abstract fun bindNewsDetailsViewModel(newsDetailsViewModel: NewsDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LeaveCommentViewModel::class)
    internal abstract fun bindLeaveCommentViewModel(leaveCommentViewModel: LeaveCommentViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel


}