package com.devspacecinenow

import kotlinx.serialization.SerialName

data class MovieDto(
    val id: String,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val postPath: String,
)