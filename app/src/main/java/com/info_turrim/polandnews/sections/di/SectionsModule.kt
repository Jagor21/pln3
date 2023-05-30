package com.info_turrim.polandnews.sections.di

import androidx.lifecycle.ViewModel
import com.info_turrim.polandnews.di.ViewModelKey
import com.info_turrim.polandnews.sections.data.repository.CategoryRepositoryImpl
import com.info_turrim.polandnews.sections.data.repository.datasource.CategoriesDataSource
import com.info_turrim.polandnews.sections.data.repository.datasource.CategoriesDataSourceImpl
import com.info_turrim.polandnews.sections.domain.CategoryRepository
import com.info_turrim.polandnews.sections.ui.fragment.SectionDetailsFragment
import com.info_turrim.polandnews.sections.ui.fragment.SectionsFragment
import com.info_turrim.polandnews.sections.ui.view_model.SectionDetailsViewModel
import com.info_turrim.polandnews.sections.ui.view_model.SectionsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class SectionsModule {

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSectionsFragment(): SectionsFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSectionDetailsFragment(): SectionDetailsFragment

    @Binds
    abstract fun bindCategoryRepository(param: CategoryRepositoryImpl): CategoryRepository

    @Binds
    abstract fun bindCategoriesDataSource(param: CategoriesDataSourceImpl): CategoriesDataSource

    @Binds
    @IntoMap
    @ViewModelKey(SectionsViewModel::class)
    internal abstract fun bindSectionsViewModel(termsViewModel: SectionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SectionDetailsViewModel::class)
    internal abstract fun bindSectionDetailsViewModel(termsViewModel: SectionDetailsViewModel): ViewModel
}