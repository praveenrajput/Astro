package com.praveen.astro.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.astro.data.repository.AstrosRepositoryInterface
import com.praveen.astro.models.IssNow
import com.praveen.astro.models.People
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AstrosViewModel(
    private val astroRepository: AstrosRepositoryInterface
) : ViewModel() {
    private var _people = MutableLiveData<List<People>>()
    val peopleLiveData: LiveData<List<People>> = _people

    private var _issNow = MutableLiveData<IssNow>()
    val issNowLiveData: LiveData<IssNow> = _issNow

    private var _peopleWithName = MutableLiveData<People>()
    val peopleWithName: LiveData<People> = _peopleWithName

    fun loadAstroWithName(name: String) = viewModelScope.launch {
        astroRepository.getPeopleWithName(name).collect {
            _peopleWithName.value = it
        }
    }

    init {
        viewModelScope.launch {
            astroRepository.getPeople().collect {
                _people.value = it
            }
        }

        viewModelScope.launch {
            astroRepository.getIssNow().collect {
                _issNow.value = it
            }
        }

        viewModelScope.launch {
            astroRepository.refreshPeople()
        }
    }
}
