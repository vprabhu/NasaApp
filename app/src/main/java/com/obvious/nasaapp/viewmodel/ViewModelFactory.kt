package com.obvious.nasaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.obviouc.network.api.ApiHelper
import com.obvious.nasaapp.repository.NasaRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NasaViewModel::class.java)) {
            return NasaViewModel(NasaRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}

