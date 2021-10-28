package com.rekyb.jyro.utils

import android.content.Context
import androidx.startup.Initializer
import com.rekyb.jyro.BuildConfig
import timber.log.Timber

class TimberInitializer: Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("Timber has been planted!")
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}