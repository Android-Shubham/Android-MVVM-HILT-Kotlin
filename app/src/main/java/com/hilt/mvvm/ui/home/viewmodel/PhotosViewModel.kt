package com.hilt.mvvm.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.hilt.mvvm.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    repository: DataRepository
) : ViewModel() {

    val characters = repository.getAllData()
}