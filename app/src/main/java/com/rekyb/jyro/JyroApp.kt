package com.rekyb.jyro

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.startup.AppInitializer
import com.rekyb.jyro.domain.use_case.data_store.GetThemeUseCase
import com.rekyb.jyro.utils.ThemeChanger
import com.rekyb.jyro.utils.TimberInitializer
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class JyroApp : Application() {

    @Inject
    lateinit var getThemeUseCase: GetThemeUseCase
    private lateinit var lifecycleOwner: LifecycleOwner

    override fun onCreate() {
        super.onCreate()

        AppInitializer.getInstance(applicationContext)
            .initializeComponent(TimberInitializer::class.java)

        appThemeController()
    }

    private fun appThemeController() {
        lifecycleOwner = ProcessLifecycleOwner.get()
        lifecycleOwner.lifecycleScope.launchWhenCreated {
            val themeSelection = getThemeUseCase()
            val themeChanger = ThemeChanger()

            themeSelection?.let { theme ->
                themeChanger.changeTo(theme)
            }

            Timber.d("Theme changed to $themeSelection")
        }
    }
}
