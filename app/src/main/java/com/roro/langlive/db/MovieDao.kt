package com.roro.langlive.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.roro.langlive.model.MovieModel

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun loadPopularMovies(): LiveData<List<MovieModel>>

    @Query("select * from movies where movieId = :movieId")
    fun loadMovie(movieId: Int): LiveData<MovieModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopularMovies(movies : List<MovieModel>?)

    @Query("DELETE FROM movies")
    fun deleteAll()

    @Query("UPDATE movies SET isFavorite=:isFavorite WHERE movieId = :movieId")
    fun updateFavorite(movieId: Int, isFavorite: Boolean)

}