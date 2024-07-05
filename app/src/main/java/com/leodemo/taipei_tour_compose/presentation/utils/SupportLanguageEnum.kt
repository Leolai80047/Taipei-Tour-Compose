package com.leodemo.taipei_tour_compose.presentation.utils

import java.util.Locale

enum class SupportLanguageEnum {

    ZH_TW,
    ZH_CN,
    EN,
    JA,
    KO,
    ES,
    ID,
    TH,
    VI;

    companion object {
        private fun getLanguage(language: SupportLanguageEnum): String {
            return when (language) {
                ZH_TW -> "zh-tw"
                ZH_CN -> "zh-cn"
                EN -> "en"
                JA -> "ja"
                KO -> "ko"
                ES -> "es"
                ID -> "id"
                TH -> "th"
                VI -> "vi"
            }
        }

        fun getLocale(language: String): Locale {
            return when (language) {
                "zh-tw" -> Locale.TAIWAN
                "zh-cn" -> Locale.CHINA
                "en" -> Locale.US
                "ja" -> Locale.JAPAN
                "ko" -> Locale.KOREA
                "es" -> Locale("es", "ES")
                "id" -> Locale("in", "ID")
                "th" -> Locale("th", "TH")
                "vi" -> Locale("vi", "VN")
                else -> Locale.TAIWAN
            }
        }

        fun getLanguage(locale: Locale): String {
            if (locale.language != "zh") return locale.language.lowercase()
            return "${locale.language.lowercase()}-${locale.country.lowercase()}"
        }

        fun getSupportLanguageList(): List<String> {
            return entries.map {
                getLanguage(it)
            }
        }
    }
}