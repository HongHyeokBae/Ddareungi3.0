package com.example.ddareungi

import com.example.ddareungi.data.AppDatabase
import com.example.ddareungi.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App: DaggerApplication() {

    private fun getDatabase() : AppDatabase {
        return AppDatabase.getInstance(this)
    }

    fun getDataRepository() : DataRepository {
        return DataRepository.getInstance(getDatabase().bookmarkStationDao())!!
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }
}