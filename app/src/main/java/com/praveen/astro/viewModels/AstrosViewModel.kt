package com.praveen.astro.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.praveen.astro.models.Astros

class AstrosViewModel : ViewModel() {
    private val _astros = MutableLiveData<Astros>()
    val astrosLiveData: LiveData<Astros> = _astros

    fun onListCahange(astros: Astros) {
        _astros.value = astros
    }
}
