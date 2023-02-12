package com.praveen.astro.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.astro.data.repository.AstrosRepositoryInterface
import com.praveen.astro.models.People
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AstrosViewModel(
    private val astroRepository: AstrosRepositoryInterface
) : ViewModel() {
    val peopleFlow = astroRepository.getPeople()

    private val _astroWithNameFlow = MutableStateFlow(People())
    val astroWithNameFlow = _astroWithNameFlow.asStateFlow()

    val issNowFlow = astroRepository.getIssNow()

    fun loadAstroWithName(name: String) = viewModelScope.launch {
        astroRepository.getPeopleWithName(name).collect {
            _astroWithNameFlow.emit(it)
        }
    }

    init {
        viewModelScope.launch {
            astroRepository.refreshPeople()
            astroRepository.refreshIss()
        }
    }
}
