package com.roro.langlive.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.roro.langlive.BasicApp
import com.roro.langlive.db.DataRepository
import com.roro.langlive.model.MovieModel
import org.jetbrains.annotations.NotNull

class MovieViewModel(
    application: Application,
    repository: DataRepository,
    private val movieId: Int,
) : AndroidViewModel(application) {

    private val mRepository: DataRepository
    var movie: LiveData<MovieModel>
    fun markFavorite(movie: MovieModel) {
        mRepository.markFavorite(movie)
    }

    /**
     * A creator is used to inject the movie ID into the ViewModel
     *
     *
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the movie ID can be passed in a public method.
     */
    class Factory(
        private val mApplication: Application,
        private val movieId: Int
    ) :
        ViewModelProvider.NewInstanceFactory() {
        private val mRepository: DataRepository = (mApplication as BasicApp).repository!!
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieViewModel(mApplication, mRepository, movieId) as T
        }

    }

    init {
        mRepository = (application as BasicApp).repository!!
        movie = mRepository.loadMovie(movieId)

    }

}