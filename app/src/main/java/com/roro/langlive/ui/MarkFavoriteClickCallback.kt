package com.roro.langlive.ui

import com.roro.langlive.model.MovieModel

interface MarkFavoriteClickCallback {
    fun onClick(movie: MovieModel)
}