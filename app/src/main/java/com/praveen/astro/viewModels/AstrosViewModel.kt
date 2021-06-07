package com.praveen.astro.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.astro.api.AstrosApi
import com.praveen.astro.models.Astros
import kotlinx.coroutines.launch

class AstrosViewModel(
    private val astrosApi: AstrosApi
) : ViewModel() {
    private val _astros = MutableLiveData<Astros>()
    val astrosLiveData: LiveData<Astros> = _astros

    fun onListChange(astros: Astros) {
        _astros.value = astros
    }

    fun getAstrosList() = viewModelScope.launch {
        _astros.value = astrosApi.fetchAstros()
    }
}
