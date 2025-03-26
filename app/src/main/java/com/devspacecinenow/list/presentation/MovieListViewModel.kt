package com.devspacecinenow.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.common.MovieDto
import com.devspacecinenow.common.MovieResponse
import com.devspacecinenow.common.RetrofitClient
import com.devspacecinenow.list.data.ListService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListViewModel(
    private val listService: ListService
) : ViewModel() {

    private val _uiNowPlayingMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    val uiNowPlayingMovies: StateFlow<List<MovieDto>> = _uiNowPlayingMovies

    private val _uiPopularMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    val uiPopularMovies: StateFlow<List<MovieDto>> = _uiPopularMovies

    private val _uiTopRatedMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    val uiTopRatedMovies: StateFlow<List<MovieDto>> = _uiTopRatedMovies

    private val _uiUpcomingMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    val uiUpcomingMovies: StateFlow<List<MovieDto>> = _uiUpcomingMovies

    init {
        fetchNowPlayingMovies()
        fetchPopularMovies()
        fetchTopRatedMovies()
        fetchUpcomingMovies()
    }

    private fun fetchNowPlayingMovies() {
        listService.getNowPlayingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiNowPlayingMovies.value = movies
                    }
                } else {
                    Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MainActivity", "Network Error :: ${t.message}")
            }
        })
    }

    private fun fetchPopularMovies() {
        listService.getPopularMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiPopularMovies.value = movies
                    }
                } else {
                    Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MainActivity", "Network Error :: ${t.message}")
            }

        })

    }

    private fun fetchTopRatedMovies() {
        listService.getTopRatedMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiTopRatedMovies.value = movies
                    }
                } else {
                    Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MainActivity", "Network Error :: ${t.message}")
            }

        })
    }

    private fun fetchUpcomingMovies() {
        listService.getUpcomingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiUpcomingMovies.value = movies
                    }
                } else {
                    Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MainActivity", "Network Error :: ${t.message}")
            }

        })
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)
                return MovieListViewModel(
                     listService
                ) as T
            }
        }
    }

}