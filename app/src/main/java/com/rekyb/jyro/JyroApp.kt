package com.rekyb.jyro

import android.app.Application
import androidx.startup.AppInitializer
import com.rekyb.jyro.utils.TimberInitializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JyroApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AppInitializer.getInstance(applicationContext)
            .initializeComponent(TimberInitializer::class.java)
    }
}
