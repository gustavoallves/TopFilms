package com.devspacecinenow.list.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspacecinenow.common.MovieDto
import com.devspacecinenow.list.presentation.MovieListViewModel

@Composable
fun MovieListScreen(
    navController: NavHostController, listViewModel: MovieListViewModel
) {
    val nowPlayingMovies by listViewModel.uiNowPlayingMovies.collectAsState()
    val popularMovies by listViewModel.uiPopularMovies.collectAsState()
    val topRatedMovies by listViewModel.uiTopRatedMovies.collectAsState()
    val upcomingMovies by listViewModel.uiUpcomingMovies.collectAsState()

    MovieListContent(
        nowPlayingMovies = nowPlayingMovies,
        popularMovies = popularMovies,
        topRatedMovies = topRatedMovies,
        upcomingMovies = upcomingMovies
    ) { itemClicked ->
        navController.navigate(route = "movieDetail/${itemClicked.id}")
    }
}

@Composable
private fun MovieListContent(
    nowPlayingMovies: List<MovieDto>,
    popularMovies: List<MovieDto>,
    topRatedMovies: List<MovieDto>,
    upcomingMovies: List<MovieDto>,
    onClick: (MovieDto) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            text = "CineNow"
        )

        MovieSession(
            label = "Popular Movies", movieList = popularMovies, onClick = onClick
        )

        MovieSession(
            label = "Now Playing", movieList = nowPlayingMovies, onClick = onClick
        )

        MovieSession(
            label = "Top Rated", movieList = topRatedMovies, onClick = onClick
        )

        MovieSession(
            label = "Upcoming Movies", movieList = upcomingMovies, onClick = onClick
        )
    }
}

@Composable
private fun MovieSession(
    label: String, movieList: List<MovieDto>, onClick: (MovieDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            fontSize = 24.sp, text = label, fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.size(8.dp))
        MovieList(
            movieList = movieList, onClick = onClick
        )
    }
}

@Composable
private fun MovieList(
    movieList: List<MovieDto>, onClick: (MovieDto) -> Unit
) {
    LazyRow {
        items(movieList) {
            MovieItem(
                movieDto = it, onClick = onClick
            )
        }

    }
}

@Composable
private fun MovieItem(
    movieDto: MovieDto, onClick: (MovieDto) -> Unit
) {

    Column(modifier = Modifier
        .width(IntrinsicSize.Min)
        .clickable {
            onClick.invoke(movieDto)
        }) {
        AsyncImage(
            modifier = Modifier
                .padding(end = 4.dp)
                .width(120.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = movieDto.posterFullPath,
            contentDescription = "${movieDto.title} Poster image"
        )
        Spacer(
            modifier = Modifier.size(4.dp)
        )
        Text(
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold,
            text = movieDto.title
        )
        Text(
            overflow = TextOverflow.Ellipsis, maxLines = 2, text = movieDto.overview
        )
    }
}