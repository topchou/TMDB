package com.roro.langlive.response

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.roro.langlive.model.MovieModel

class PopularMoviesResult {
    //https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1
    @SerializedName("results")
    @Expose
    var movies: List<MovieModel>? = null
    override fun toString(): String {
        return "Popular Movies Response ={ {$movies}}"
    }

}