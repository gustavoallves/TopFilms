package com.devspacecinenow

import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("now_playing?language=en-US&page=1")
    fun getNowPlayingMovies() : Call<Unit>

}