package com.leodemo.taipei_tour_compose.presentation.utils

import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

object LocaleUtils {

    const val DEFAULT_LANGUAGE = "zh-tw"
    fun setLocale(
        context: Context,
        locale: Locale
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val localeManager = context.getSystemService(LocaleManager::class.java)
            localeManager.applicationLocales = LocaleList(locale)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.create(locale))
        }
    }

    fun getLocale(context: Context): LocaleListCompat {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val localeManager = context.getSystemService(LocaleManager::class.java)
            val localeList = localeManager.applicationLocales
            if (localeList.isEmpty) {
                LocaleListCompat.wrap(localeManager.systemLocales)
            } else {
                LocaleListCompat.wrap(localeManager.applicationLocales)
            }
        } else {
            val localeList = AppCompatDelegate.getApplicationLocales()
            if (localeList.isEmpty) {
                LocaleListCompat.getDefault()
            } else {
                localeList
            }
        }
    }

    private fun getLocalizeConfiguration(locale: Locale): Configuration {
        val config = Configuration()
        return config.apply {
            setLayoutDirection(locale)
            setLocale(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                setLocales(localeList)
            }
        }
    }

    fun getLocalizeContext(baseContext: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        val config = getLocalizeConfiguration(locale)
        return baseContext.createConfigurationContext(config)
    }
}