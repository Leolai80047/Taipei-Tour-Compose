package com.leodemo.taipei_tour_compose.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.leodemo.taipei_tour.data.repository.attraction.AttractionInteractor
import com.leodemo.taipei_tour_compose.domain.model.AttractionInfo
import com.leodemo.taipei_tour_compose.domain.usecase.GetSettingLanguageUseCase
import com.leodemo.taipei_tour_compose.domain.usecase.PutSettingLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getSettingLanguageUseCase: GetSettingLanguageUseCase,
    private val putSettingLanguageUseCase: PutSettingLanguageUseCase
) : ViewModel() {
    var currentLanguage: String = getSettingLanguageUseCase()
        set(value) {
            putSettingLanguageUseCase(value)
            field = value
        }
}