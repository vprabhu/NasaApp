package com.obvious.nasaapp.repository

import com.obviouc.network.api.ApiHelper

class NasaRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()
}