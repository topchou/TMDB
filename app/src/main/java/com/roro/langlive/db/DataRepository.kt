package com.roro.langlive.db

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.roro.langlive.BuildConfig
import com.roro.langlive.model.MovieModel
import com.roro.langlive.model.PopularBody
import com.roro.langlive.network.MovieApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.roro.langlive.response.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataRepository private constructor(private val mDatabase: AppDatabase) {
    var token = "no"
    var sessionId = "no"

    fun loadPopularMovies(): LiveData<List<MovieModel>> {
        return mDatabase.movieDao().loadPopularMovies()
    }

    fun loadMovie(movieId: Int): LiveData<MovieModel> {
        return mDatabase.movieDao().loadMovie(movieId)
    }

    fun markDbFavorite(movieId: Int, isFavorite: Boolean) {
        mDatabase.movieDao().updateFavorite(movieId, isFavorite)
    }

    init {
        requestMovies()
    }

    fun requestToken(context: Context) {
        MovieApi.retrofitService.getToken(BuildConfig.API_KEY)
            .enqueue(object : Callback<TokenResult> {
                override fun onResponse(call: Call<TokenResult>, response: Response<TokenResult>) {
                    Log.d(TAG, "token response.code = ${response.code()} !!! ")
                    if (response.code() == 200) {
                        Log.d(TAG, "token = ${response.body().toString()}")
                        token = response.body()?.token ?: "no"
                        approve(context)
                    }
                }

                override fun onFailure(call: Call<TokenResult>, t: Throwable) {
                    Log.d(TAG, "token response fail!!! ")
                }

            })
    }

    fun approve(context: Context) {
        if (token != "no") {
            val intent = Intent(Intent.ACTION_VIEW)
            Log.d(TAG, "approve token = $token")
            intent.data = Uri.parse("https://www.themoviedb.org/authenticate/$token")
            context.startActivity(intent)
        }

    }


    fun requestSession() {
        MovieApi.retrofitService.getSession(BuildConfig.API_KEY, token)
            .enqueue(object : Callback<SessionResult> {
                override fun onResponse(
                    call: Call<SessionResult>,
                    response: Response<SessionResult>,
                ) {
                    Log.d(TAG, "session response.code = ${response.code()} !!! ")
                    if (response.code() == 200) {
                        Log.d(TAG, "session = ${response.body().toString()}")
                        sessionId = response.body()?.sessionId ?: "no"
                        requestFavorites()
                    }
                }

                override fun onFailure(call: Call<SessionResult>, t: Throwable) {
                    Log.d(TAG, "token response fail!!! ")
                }

            })
    }

    private fun requestMovies() {
        MovieApi.retrofitService.getMovies(BuildConfig.API_KEY, "en-US", 1)
            .enqueue(object : Callback<PopularMoviesResult?> {
                override fun onResponse(
                    call: Call<PopularMoviesResult?>,
                    response: Response<PopularMoviesResult?>,
                ) {
                    if (response.code() == 200) {
                        Log.d(TAG, "response.body = ${response.body().toString()}")
                        //moviesResult.postValue(response.body()?.movies!!)
                        GlobalScope.launch {
                            mDatabase.movieDao().deleteAll()
                            mDatabase.movieDao().insertPopularMovies(response.body()?.movies)
                        }
                    }
                    Log.d(TAG, "response.code = ${response.code()} !!! ")
                }

                override fun onFailure(call: Call<PopularMoviesResult?>, t: Throwable) {
                    Log.d(TAG, "response.fail !!! $t")
                }
            })
    }

    fun requestFavorites() {
        MovieApi.retrofitService.getFavoriteMovies(
            BuildConfig.AccountId,
            BuildConfig.API_KEY,
            sessionId
        ).enqueue(object : Callback<FavoriteMoviesResult> {
            override fun onResponse(
                call: Call<FavoriteMoviesResult>,
                response: Response<FavoriteMoviesResult>
            ) {
                if (response.code() == 200) {
                    Log.d(TAG, "response.body = ${response.body().toString()}")
                    GlobalScope.launch {
                        response.body()?.movies?.forEach { movie ->
                            mDatabase.movieDao().updateFavorite(movie.movieId, true)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<FavoriteMoviesResult>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun markFavorite(movie: MovieModel) {
        Log.d(
            TAG,
            "markFavorite id = ${movie.movieId} , sessionId = $sessionId, movie.isFavorite =${movie.isFavorite}"
        )
        val markMovie = PopularBody("movie", movie.movieId, !(movie.isFavorite))
        //Todo check Token not expire
        MovieApi.retrofitService.markAsFavorite(
            BuildConfig.AccountId,
            markMovie,
            BuildConfig.API_KEY,
            sessionId
        )
            ?.enqueue(object : Callback<MarkFavoriteResult?> {
                override fun onResponse(
                    call: Call<MarkFavoriteResult?>,
                    response: Response<MarkFavoriteResult?>,
                ) {
                    Log.d(TAG, "markFavorite response.code = ${response.code()} !!! ")
                    if (response.code() == 200 || response.code() == 201) {
                        Log.d(TAG, "Mark favorite success")
                        GlobalScope.launch {
                            markDbFavorite(movie.movieId, !movie.isFavorite)
                        }
                    }
                }

                override fun onFailure(call: Call<MarkFavoriteResult?>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    companion object {
        val TAG = "Data"
        private var sInstance: DataRepository? = null
        fun getInstance(database: AppDatabase): DataRepository? {
            if (sInstance == null) {
                synchronized(DataRepository::class.java) {
                    if (sInstance == null) {
                        sInstance = DataRepository(database)
                    }
                }
            }
            return sInstance
        }
    }
}