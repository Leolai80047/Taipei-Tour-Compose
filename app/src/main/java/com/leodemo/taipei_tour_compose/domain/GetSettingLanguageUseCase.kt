package com.leodemo.taipei_tour_compose.domain

import com.leodemo.taipei_tour.data.repository.language.AttractionLanguageInteractor
import javax.inject.Inject

class GetSettingLanguageUseCase @Inject constructor(
    private val attractionLanguageRepository: AttractionLanguageInteractor
) {
    operator fun invoke(): String {
        return attractionLanguageRepository.getLastLanguage()
    }
}