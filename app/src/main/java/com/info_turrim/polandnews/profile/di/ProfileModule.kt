package com.info_turrim.polandnews.profile.di

import androidx.lifecycle.ViewModel
import com.info_turrim.polandnews.di.ViewModelKey
import com.info_turrim.polandnews.news_feed.domain.ProfileRepository
import com.info_turrim.polandnews.profile.ui.fragment.*
import com.info_turrim.polandnews.profile.ui.view_model.*
import com.info_turrim.polandnews.profile.data.repository.ProfileRepositoryImpl
import com.info_turrim.polandnews.profile.data.repository.datasource.ProfileDataSource
import com.info_turrim.polandnews.profile.data.repository.datasource.ProfileDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ProfileModule {

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeProfileFragment(): ProfileFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeAuthFragment(): AuthFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSignInFragment(): SignInFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSignUpFragment(): SignUpFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeStartAuthFragment(): StartAuthFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeFavoritesFragment(): FavouritesFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeProfileSectionsFragment(): ProfileSectionsFragment

    @com.info_turrim.polandnews.di.FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeHideSectionBottomSheetDialogFragment():
            HideSectionBottomSheetDialogFragment

    @Binds
    abstract fun bindProfileRepository(param: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindProfileDataSource(param: ProfileDataSourceImpl): ProfileDataSource

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    internal abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    internal abstract fun bindSignInViewModel(signInViewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    internal abstract fun bindSignUpViewModel(signUpViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StartAuthViewModel::class)
    internal abstract fun bindStartAuthViewModel(startAuthViewModel: StartAuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    internal abstract fun bindFavoritesViewModel(favoritesViewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileSectionsViewModel::class)
    internal abstract fun bindProfileSectionsViewModel(profileSectionsViewModel: ProfileSectionsViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(HideSectionBottomSheetDialogViewModel::class)
    internal abstract fun bindHideSectionBottomSheetDialogViewModel(
        hideSectionBottomSheetDialogViewModel: HideSectionBottomSheetDialogViewModel
    ): ViewModel
}