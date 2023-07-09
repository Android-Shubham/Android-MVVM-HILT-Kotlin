package com.hilt.mvvm.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.hilt.mvvm.ui.home.viewmodel.PhotosViewModel

@Composable
fun PhotoDetails(viewModel: PhotosViewModel) {
    val clickedPhoto = viewModel.clickedPhoto
    Column(

        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SubcomposeAsyncImage(
            model = clickedPhoto.url, contentDescription = null, loading = {
                CircularProgressIndicator()
            }, alignment = Alignment.Center
        )
        Text(
            text = clickedPhoto.title, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
        )
    }
}