package com.leodemo.taipei_tour_compose.ui.screens.attraction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.leodemo.taipei_tour.data.repository.attraction.AttractionInteractor
import com.leodemo.taipei_tour_compose.domain.model.toDomain
import com.leodemo.taipei_tour_compose.domain.usecase.PutSettingLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AttractionViewModel @Inject constructor(
    attractionRepository: AttractionInteractor,
    private val putSettingLanguageUseCase: PutSettingLanguageUseCase
) : ViewModel() {
    var attractionPager = attractionRepository.getAttractionPagingSource().map { pagingData ->
        pagingData.map { response ->
            response.toDomain()
        }
    }.cachedIn(viewModelScope)

    fun setLanguage(language: String) {
        putSettingLanguageUseCase(language)
    }
}