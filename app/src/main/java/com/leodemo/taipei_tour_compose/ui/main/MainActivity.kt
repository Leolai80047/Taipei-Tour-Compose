package com.leodemo.taipei_tour_compose.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.leodemo.taipei_tour_compose.ui.utils.language.LocaleUtils
import com.leodemo.taipei_tour_compose.ui.utils.language.SupportLanguageEnum
import com.leodemo.taipei_tour_compose.ui.navigation.AttractionNavigation
import com.leodemo.taipei_tour_compose.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        initLocaleSetting()
        setContent {
            AppTheme {
                AttractionNavigation(viewModel)
            }
        }
    }

    private fun initLocaleSetting() {
        if (viewModel.currentLanguage.isNotEmpty()) return

        val locale = LocaleUtils.getLocale(this).get(0) ?: throw Exception("Locale is empty!")
        val language = SupportLanguageEnum.getLanguage(locale)
        val chosenLanguage =
            SupportLanguageEnum.getSupportLanguageList().firstOrNull { supportLanguage ->
                supportLanguage == language
            } ?: LocaleUtils.DEFAULT_LANGUAGE
        val chosenLocale = SupportLanguageEnum.getLocale(chosenLanguage)

        viewModel.currentLanguage = chosenLanguage
        LocaleUtils.setLocale(this, chosenLocale)
    }
}