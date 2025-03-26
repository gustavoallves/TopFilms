package com.devspacecinenow.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.common.MovieDto
import com.devspacecinenow.common.RetrofitClient
import com.devspacecinenow.detail.data.DetailService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MovieDetailViewModel(
    private val detailService: DetailService
): ViewModel() {

    private var _uiMovieDetail = MutableStateFlow<MovieDto?>(null)
    val uiMovieDetail: StateFlow<MovieDto?> = _uiMovieDetail

    fun fetchMovieDetail(movieId: String) {
        if (_uiMovieDetail.value == null) {
            detailService.getMovieById(movieId).enqueue(
                object : retrofit2.Callback<MovieDto> {
                    override fun onResponse(call: Call<MovieDto>, response: Response<MovieDto>) {
                        if (response.isSuccessful) {
                            _uiMovieDetail.value = response.body()
                        } else {
                            Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                        }
                    }

                    override fun onFailure(call: Call<MovieDto>, t: Throwable) {
                        Log.d("MainActivity", "Network Error :: ${t.message}")
                    }
                }
            )
        }
    }

    fun cleanMovieId(){
        viewModelScope.launch {
            delay(1000)
            _uiMovieDetail.value = null
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService =
                    RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return MovieDetailViewModel(
                    detailService
                ) as T
            }
        }
    }
}