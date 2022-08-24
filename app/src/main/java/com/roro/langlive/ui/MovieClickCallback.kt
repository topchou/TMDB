package com.roro.langlive.ui

import com.roro.langlive.model.MovieModel

interface MovieClickCallback {
    fun onClick(movieId: Int, postPath: String)
}