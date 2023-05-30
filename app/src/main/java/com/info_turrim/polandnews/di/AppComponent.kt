package com.info_turrim.polandnews.di

import com.info_turrim.polandnews.PolandNewsApp
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
        ServiceModule::class,
        MainModule::class,
//        SharedPreferencesModule::class
    ]
)
interface AppComponent : AndroidInjector<PolandNewsApp> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<PolandNewsApp>

//    fun getContext(): Context
}