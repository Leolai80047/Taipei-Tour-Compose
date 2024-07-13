package com.leodemo.taipei_tour_compose.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.leodemo.taipei_tour_compose.domain.model.AttractionInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val item = savedStateHandle.getLiveData<AttractionInfo?>("attractionInfo", null)
}