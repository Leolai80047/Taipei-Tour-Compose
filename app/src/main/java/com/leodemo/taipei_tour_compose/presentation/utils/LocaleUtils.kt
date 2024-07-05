package com.leodemo.taipei_tour_compose.presentation.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

object LocaleUtils {
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
            LocaleListCompat.wrap(localeManager.applicationLocales)
        } else {
            val localeList = AppCompatDelegate.getApplicationLocales()
            if (localeList.isEmpty)
                LocaleListCompat.getDefault()
            else
                localeList
        }
    }
}