package com.praveen.astro.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.astro.data.repository.AstroRepository
import com.praveen.astro.models.People
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AstrosViewModel(
    private val astroRepository: AstroRepository
) : ViewModel() {
    private var _people = MutableLiveData<List<People>>()
    val peopleLiveData: LiveData<List<People>> = _people

    init {
        viewModelScope.launch {
            astroRepository.getPeople().collect {
                _people.value = it
            }
        }
    }
}
