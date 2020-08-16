package com.example.ddareungi.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class])
interface ApplicationComponent: AndroidInjector<DdaApplication> {

    @Component.Factory
    interface Factory {
        fun create(): ApplicationComponent
    }
}