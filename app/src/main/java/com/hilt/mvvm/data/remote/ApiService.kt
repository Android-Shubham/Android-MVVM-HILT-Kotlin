package com.hilt.mvvm.data.remote

import com.hilt.mvvm.data.entities.Photos
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("photos")
    suspend fun getAllData(): Response<List<Photos>>
}