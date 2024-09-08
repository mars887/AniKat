package daxo.the.anikat

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import daxo.the.anikat.core.di.AppComponent
import daxo.the.anikat.core.di.DaggerAppComponent

class App : DaggerApplication() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().withContext(applicationContext).build()
        return appComponent
    }

    companion object {
        lateinit var instance: App
    }
}