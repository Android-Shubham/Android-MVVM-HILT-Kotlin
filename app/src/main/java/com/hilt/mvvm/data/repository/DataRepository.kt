package com.hilt.mvvm.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.hilt.mvvm.data.entities.Photos
import com.hilt.mvvm.data.remote.DataSource
import com.hilt.mvvm.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val remoteDataSource: DataSource,
) {


    fun getAllData(): LiveData<Resource<List<Photos>>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val responseStatus = remoteDataSource.getAllData()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                emit(Resource.success(responseStatus.data!!))
            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(Resource.error(responseStatus.message!!))
            }
        }
    }
}