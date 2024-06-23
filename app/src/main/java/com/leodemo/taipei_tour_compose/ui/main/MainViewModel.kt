package com.leodemo.taipei_tour_compose.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leodemo.taipei_tour.data.api.AttractionResponse
import com.leodemo.taipei_tour.data.repository.attraction.AttractionInteractor
import com.leodemo.taipei_tour_core.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val attractionRepository: AttractionInteractor
): ViewModel() {

    val data = MutableLiveData<List<AttractionResponse.Data>>()

    fun getAttraction() {
        viewModelScope.launch {
            data.postValue(attractionRepository.fetchAttractions(BuildConfig.DEFAULT_LANGUAGE))
        }
    }
}