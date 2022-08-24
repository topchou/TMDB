package com.roro.langlive.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.roro.langlive.BasicApp
import com.roro.langlive.db.DataRepository
import com.roro.langlive.model.MovieModel

class MovieListViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: DataRepository
    var moviesResult: LiveData<List<MovieModel>>

    fun getMovies(): LiveData<List<MovieModel>> {
        return moviesResult
    }

    fun markFavorite(movie: MovieModel) {
        mRepository.markFavorite(movie)
    }


    init {
        mRepository = (application as BasicApp).repository!!
        moviesResult = mRepository.loadPopularMovies()

    }

}