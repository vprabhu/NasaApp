package com.obvious.nasaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.obviouc.network.utils.Resource
import com.obvious.nasaapp.repository.NasaRepository
import kotlinx.coroutines.Dispatchers


class NasaViewModel(private val nasaRepository: NasaRepository) : ViewModel() {

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = nasaRepository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}