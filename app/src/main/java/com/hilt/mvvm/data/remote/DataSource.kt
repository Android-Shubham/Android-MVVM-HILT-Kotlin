package com.hilt.mvvm.data.remote

import javax.inject.Inject

class DataSource @Inject constructor(
    private val apiService: ApiService
) : BaseDataSource() {

    suspend fun getAllData() = getResult { apiService.getAllData() }
}