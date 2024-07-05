package com.leodemo.taipei_tour_compose.domain

import com.leodemo.taipei_tour.data.repository.language.AttractionLanguageInteractor
import javax.inject.Inject

class PutSettingLanguageUseCase @Inject constructor(
    private val attractionLanguageRepository: AttractionLanguageInteractor
) {
    operator fun invoke(language: String) {
        attractionLanguageRepository.setLastLanguage(language)
    }
}