package com.leodemo.taipei_tour_compose.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.leodemo.taipei_tour.data.api.AttractionResponse
import com.leodemo.taipei_tour.data.repository.attraction.AttractionInteractor
import com.leodemo.taipei_tour_compose.domain.GetSettingLanguageUseCase
import com.leodemo.taipei_tour_compose.domain.PutSettingLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    attractionRepository: AttractionInteractor,
    getSettingLanguageUseCase: GetSettingLanguageUseCase,
    private val putSettingLanguageUseCase: PutSettingLanguageUseCase
) : ViewModel() {
    var attractionPager = attractionRepository.getAttractionPagingSource().cachedIn(viewModelScope)
    val currentItem = MutableLiveData<AttractionResponse.Data>()
    var currentLanguage: String = getSettingLanguageUseCase()
        set(value) {
            putSettingLanguageUseCase(value)
            field = value
        }
}