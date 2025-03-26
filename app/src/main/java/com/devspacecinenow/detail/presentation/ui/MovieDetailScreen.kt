package com.devspacecinenow.detail.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.devspacecinenow.common.MovieDto
import com.devspacecinenow.common.RetrofitClient
import com.devspacecinenow.detail.presentation.MovieDetailViewModel
import retrofit2.Call
import retrofit2.Response

@Composable
fun MovieDetailScreen(
    movieId: String,
    navController: NavController,
    detailViewModel: MovieDetailViewModel
) {
    val movieDto by detailViewModel.uiMovieDetail.collectAsState()
    detailViewModel.fetchMovieDetail(movieId)

    movieDto?.let {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                    detailViewModel.cleanMovieId()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back button",
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(start = 4.dp),
                    text = it.title
                )
            }
            MovieDetailContent(it)
        }
    }
}

@Composable
private fun MovieDetailContent(movie: MovieDto) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = movie.posterFullPath,
            contentDescription = "${movie.title} Poster Image"
        )

        Text(
            modifier = Modifier
                .padding(16.dp),
            text = movie.overview,
            fontSize = 16.sp
        )
    }
}