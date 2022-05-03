package com.obviouc.network.api

import com.obviouc.network.model.NasaItem
import retrofit2.http.GET

interface ApiService {

    @GET("b/83OO")
    suspend fun getUsers(): ArrayList<NasaItem>

}