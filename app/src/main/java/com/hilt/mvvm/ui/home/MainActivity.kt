package com.hilt.mvvm.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.hilt.mvvm.data.entities.Photos
import com.hilt.mvvm.ui.appnavigation.Screen
import com.hilt.mvvm.ui.details.PhotoDetails
import com.hilt.mvvm.ui.home.viewmodel.PhotosViewModel
import com.hilt.mvvm.ui.theme.MVVMHILTTheme
import com.hilt.mvvm.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMHILTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph()
                }
            }
        }
    }

    @Composable
    fun AppNavGraph() {
        val navController = rememberNavController()
        val viewModel: PhotosViewModel by viewModels()
        NavHost(navController = navController, startDestination = Screen.List.route) {
            composable(route = Screen.List.route) {
                PhotoList(navController = navController, viewModel)
            }

            composable(route = Screen.Details.route) {
                PhotoDetails(viewModel)
            }
        }
    }


    @Composable
    fun PhotoList(navController: NavHostController, viewModel: PhotosViewModel) {
        val isMapLoading = remember { mutableStateOf(true) }

        val favourites = remember { mutableStateListOf<Photos>() }

        getData(favourites, isMapLoading, viewModel)


        LazyColumn {
            items(favourites) { item ->
                RenderListItem(item, navController, viewModel)
            }
        }

        if (isMapLoading.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }


    }

    @Composable
    private fun RenderListItem(
        photos: Photos,
        navController: NavHostController,
        viewModel: PhotosViewModel
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp)
        ) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    viewModel.clickedPhoto = photos
                    navController.navigate(Screen.Details.route)
                }) {
                Row(modifier = Modifier.padding(12.dp)) {
                    AsyncImage(
                        model = photos.thumbnailUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp)
                    )
                    Text(
                        text = photos.title,
                        modifier = Modifier.padding(start = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    private fun getData(
        favourites: SnapshotStateList<Photos>,
        isMapLoading: MutableState<Boolean>,
        viewModel: PhotosViewModel
    ) {
        viewModel.characters.observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    isMapLoading.value = false
                    if (!it.data.isNullOrEmpty()) {
                        favourites.addAll(it.data)
                    }
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    isMapLoading.value = false
                }

                Resource.Status.LOADING -> {
                    isMapLoading.value = true
                }
            }
        }
    }


}


